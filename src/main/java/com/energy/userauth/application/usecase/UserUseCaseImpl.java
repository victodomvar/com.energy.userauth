package com.energy.userauth.application.usecase;

import com.energy.userauth.application.port.in.UserUseCase;
import com.energy.userauth.application.port.out.UserRepositoryPort;
import com.energy.userauth.domain.exception.UserAlreadyExistsException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.service.UserDomainService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserUseCaseImpl implements UserUseCase {
    private final UserDomainService userDomainService;
    private final UserRepositoryPort userRepository;

    public UserUseCaseImpl(UserDomainService userDomainService, UserRepositoryPort userRepository) {
        this.userDomainService = userDomainService;
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        userDomainService.validateUserCreation(user);
        userRepository.findByEmail(user.getEmail())
                .ifPresent(foundUser -> {
                    throw new UserAlreadyExistsException(
                            "A user with email " + user.getEmail() + " already exists.");
                });
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        if (user.getStatus() != null) {
            existingUser.setStatus(user.getStatus());
        }
        userDomainService.validateUserCreation(existingUser);

        return userRepository.save(existingUser);
    }

    @Override
    public List<User> getUser(Long id) {
        if (id != null) {
            User foundUser = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
            return Collections.singletonList(foundUser);
        }
        return userRepository.findAll();
    }
}
