package org.storage.metadata.validator;

import org.springframework.stereotype.Service;
import org.storage.metadata.exception.UserNotExistException;
import org.storage.metadata.repository.UserRepository;

@Service
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateDeleteUser(String userName) {
        if (!userRepository.existsByUsername(userName)) {
            throw new UserNotExistException();
        }
    }

    public void validateUser(String usernameOrEmail) {
        if (!userRepository.existsByUsername(usernameOrEmail) && !userRepository.existsByEmail(usernameOrEmail)) {
            throw new UserNotExistException();
        }
    }
}
