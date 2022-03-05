package org.storage.metadata.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.storage.metadata.model.User;
import org.storage.metadata.repository.UserRepository;
import org.storage.metadata.worker.dto.LoginDTO;
import org.storage.metadata.worker.dto.SignUpDTO;

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

    UserAuthService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
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

}
