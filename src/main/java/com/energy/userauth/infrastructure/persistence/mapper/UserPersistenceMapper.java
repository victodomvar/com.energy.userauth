package com.energy.userauth.infrastructure.persistence.mapper;

import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.model.UserStatus;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserPersistenceMapper {

    public UserEntity domainToEntity(User user) {
        if (user == null) {
            return null;
        }
        return new UserEntity(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getStatus() == null ? UserStatus.ACTIVE.name() : user.getStatus().name(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User entityToDomain(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getEmail(),
                userEntity.getStatus() == null ? UserStatus.ACTIVE : UserStatus.valueOf(userEntity.getStatus()),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt()
        );
    }

    public List<User> entityListToDomainList(List<UserEntity> entities) {
        return entities.stream().map(this::entityToDomain).toList();
    }
}
