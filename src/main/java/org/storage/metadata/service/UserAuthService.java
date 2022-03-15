package org.storage.metadata.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.storage.metadata.model.Role;
import org.storage.metadata.model.User;
import org.storage.metadata.repository.RoleRepository;
import org.storage.metadata.repository.UserRepository;
import org.storage.metadata.model.orchestrator.dto.LoginDTO;
import org.storage.metadata.model.orchestrator.dto.SignUpDTO;
import org.storage.metadata.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserAuthService {
    public enum USER_AUTH_ACTION {
        USERNAME_TAKEN,
        EMAIL_TAKEN,
        REGISTER_SUCCESSFUL,
        SIGNIN_SUCCESS,
        SIGNIN_FAIL
    }
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final RoleRepository roleRepository;

    UserAuthService(RoleRepository roleRepository, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userValidator = userValidator;
        this.roleRepository = roleRepository;
    }

    public USER_AUTH_ACTION registerUser(SignUpDTO signUpDto) {
        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            return USER_AUTH_ACTION.USERNAME_TAKEN;
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return USER_AUTH_ACTION.EMAIL_TAKEN;
        }

        createUser(signUpDto);
        return USER_AUTH_ACTION.REGISTER_SUCCESSFUL;
    }

    public void createUser(SignUpDTO signUpDto) {
        User user = new User();
        user.setUsername(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        if(!roleRepository.existsById(1L)) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
        }

        user.setRole(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }

    public USER_AUTH_ACTION authenticateUser(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return USER_AUTH_ACTION.SIGNIN_SUCCESS;
        } catch(Exception e) {
            e.printStackTrace();
            return USER_AUTH_ACTION.SIGNIN_FAIL;
        }
    }

    public void deleteUser(String userName) {
        userValidator.validateDeleteUser(userName);
        userRepository.deleteByUsername(userName);
    }

    public Optional<User> getUserByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    public List<User> getAllUsers() {
        List<User> Users = new ArrayList<User>();
        try {
            Users.addAll(userRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Users;
    }

}
