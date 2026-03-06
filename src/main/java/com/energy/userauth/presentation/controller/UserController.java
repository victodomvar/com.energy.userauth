package com.energy.userauth.presentation.controller;

import com.energy.userauth.application.port.in.UserUseCase;
import com.energy.userauth.openapi.api.UserApi;
import com.energy.userauth.openapi.model.CreateUserRequest;
import com.energy.userauth.openapi.model.UpdateUserRequest;
import com.energy.userauth.openapi.model.UserDto;
import com.energy.userauth.presentation.mapper.UserApiMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserUseCase userUseCase;
    private final UserApiMapper userApiMapper;

    public UserController(UserUseCase userUseCase, UserApiMapper userApiMapper) {
        this.userUseCase = userUseCase;
        this.userApiMapper = userApiMapper;
    }

    @Override
    public ResponseEntity<UserDto> createUser(CreateUserRequest createUserRequest) {
        UserDto createdUser = userApiMapper.domainToApiDto(
                userUseCase.createUser(userApiMapper.createRequestToDomain(createUserRequest))
        );
        return ResponseEntity.ok(createdUser);
    }

    @Override
    public ResponseEntity<List<UserDto>> getUser(Long id) {
        return ResponseEntity.ok(userApiMapper.domainListToApiList(userUseCase.getUser(id)));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long id, UpdateUserRequest updateUserRequest) {
        UserDto updatedUser = userApiMapper.domainToApiDto(
                userUseCase.updateUser(id, userApiMapper.updateRequestToDomain(updateUserRequest))
        );
        return ResponseEntity.ok(updatedUser);
    }
}
