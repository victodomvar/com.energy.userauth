package com.energy.userauth.application.port;

import com.energy.userauth.domain.model.User;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
