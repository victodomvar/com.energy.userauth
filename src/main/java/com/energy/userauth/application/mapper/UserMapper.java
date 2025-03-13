package com.energy.userauth.application.mapper;
import org.mapstruct.Mapper;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;

@Mapper( componentModel = "spring")
public interface UserMapper {
    User toDomain(com.energy.userauth.openapi.model.UserDto apiUserDto);
    com.energy.userauth.presentation.dto.UserDto toPresentation(User user);
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
    com.energy.userauth.openapi.model.UserDto  toUserApiDto(User user);

}
