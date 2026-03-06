package com.energy.userauth.infrastructure.persistence;

import com.energy.userauth.application.port.out.UserRepositoryPort;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;
import com.energy.userauth.infrastructure.persistence.mapper.UserPersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserPersistenceMapper userMapper;
    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryAdapter(UserPersistenceMapper userMapper, JpaUserRepository jpaUserRepository) {
        this.userMapper = userMapper;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = jpaUserRepository.save(userMapper.domainToEntity(user));
        return userMapper.entityToDomain(userEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id)
                .map(userMapper::entityToDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(userMapper::entityToDomain);
    }

    @Override
    public List<User> findAll() {
        return userMapper.entityListToDomainList(jpaUserRepository.findAll());
    }
}
