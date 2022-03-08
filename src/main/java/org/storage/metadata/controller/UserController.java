package org.storage.metadata.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.storage.metadata.model.User;
import org.storage.metadata.repository.UserRepository;
import org.storage.metadata.service.UserAuthService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserRepository userRepository;
    private UserAuthService userAuthService;

    UserController(UserRepository userRepository, UserAuthService userAuthService) {
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
    }

    @GetMapping("/username")
    public ResponseEntity<?> getUserByUserName(final String userName) {
        Optional<User> user = userRepository.findByUsername(userName);

        if(user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> Users = new ArrayList<User>();
            userRepository.findAll().forEach(Users::add);
            if (Users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(Users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/username")
    public ResponseEntity<?> deleteUserByUserName(final String userName) {

        userAuthService.deleteUser(userName);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
