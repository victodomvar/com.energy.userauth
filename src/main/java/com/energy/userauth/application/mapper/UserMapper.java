package com.energy.userauth.application.mapper;
import org.mapstruct.Mapper;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Mapper( componentModel = "spring")
public interface UserMapper {
    User apiDtoToDomain(com.energy.userauth.openapi.model.UserDto apiUserDto);
    com.energy.userauth.presentation.dto.UserDto domainToPresentation(User user);
    UserEntity domainToEntity(User user);
    User entityToDomain(UserEntity entity);
    com.energy.userauth.openapi.model.UserDto  domainToUserApiDto(User user);
    List<com.energy.userauth.openapi.model.UserDto> domainToApiDtoList(List<User> userList);
    List<User> entityToApiDtoList(List<UserEntity> userEntityList);

}
