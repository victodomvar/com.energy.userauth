package com.energy.userauth.presentation.mapper;

import com.energy.userauth.domain.model.User;
import com.energy.userauth.openapi.model.CreateUserRequest;
import com.energy.userauth.openapi.model.UpdateUserRequest;
import com.energy.userauth.openapi.model.UserDto;
import com.energy.userauth.openapi.model.UserStatus;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class UserApiMapper {

    public User createRequestToDomain(CreateUserRequest request) {
        if (request == null) {
            return null;
        }
        return new User(
                null,
                request.getUserName(),
                request.getEmail(),
                request.getStatus() == null
                        ? com.energy.userauth.domain.model.UserStatus.ACTIVE
                        : com.energy.userauth.domain.model.UserStatus.valueOf(request.getStatus().getValue()),
                null,
                null
        );
    }

    public User updateRequestToDomain(UpdateUserRequest request) {
        if (request == null) {
            return null;
        }
        return new User(
                null,
                request.getUserName(),
                request.getEmail(),
                request.getStatus() == null
                        ? null
                        : com.energy.userauth.domain.model.UserStatus.valueOf(request.getStatus().getValue()),
                null,
                null
        );
    }

    public UserDto domainToApiDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto()
                .id(user.getId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .status(UserStatus.fromValue(user.getStatus().name()));
        if (user.getCreatedAt() != null) {
            userDto.setCreatedAt(OffsetDateTime.ofInstant(user.getCreatedAt(), ZoneOffset.UTC));
        }
        if (user.getUpdatedAt() != null) {
            userDto.setUpdatedAt(OffsetDateTime.ofInstant(user.getUpdatedAt(), ZoneOffset.UTC));
        }
        return userDto;
    }

    public List<UserDto> domainListToApiList(List<User> users) {
        return users.stream().map(this::domainToApiDto).toList();
    }
}
