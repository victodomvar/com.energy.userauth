package com.energy.userauth.presentation.controller;

import com.energy.userauth.application.port.in.IdentityLinkUseCase;
import com.energy.userauth.application.port.in.UserUseCase;
import com.energy.userauth.openapi.api.IdentityLinkApi;
import com.energy.userauth.openapi.api.UserApi;
import com.energy.userauth.openapi.model.CreateIdentityLinkRequest;
import com.energy.userauth.openapi.model.CreateUserRequest;
import com.energy.userauth.openapi.model.IdentityLinkDto;
import com.energy.userauth.openapi.model.UpdateUserRequest;
import com.energy.userauth.openapi.model.UserDto;
import com.energy.userauth.presentation.mapper.IdentityLinkApiMapper;
import com.energy.userauth.presentation.mapper.UserApiMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi, IdentityLinkApi {

    private final UserUseCase userUseCase;
    private final IdentityLinkUseCase identityLinkUseCase;
    private final UserApiMapper userApiMapper;
    private final IdentityLinkApiMapper identityLinkApiMapper;

    public UserController(UserUseCase userUseCase,
                          IdentityLinkUseCase identityLinkUseCase,
                          UserApiMapper userApiMapper,
                          IdentityLinkApiMapper identityLinkApiMapper) {
        this.userUseCase = userUseCase;
        this.identityLinkUseCase = identityLinkUseCase;
        this.userApiMapper = userApiMapper;
        this.identityLinkApiMapper = identityLinkApiMapper;
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

    @Override
    public ResponseEntity<IdentityLinkDto> createIdentityLink(Long id,
                                                               CreateIdentityLinkRequest createIdentityLinkRequest) {
        IdentityLinkDto identityLinkDto = identityLinkApiMapper.domainToApiDto(
                identityLinkUseCase.createIdentityLink(
                        id,
                        identityLinkApiMapper.createRequestToDomain(createIdentityLinkRequest)
                )
        );
        return ResponseEntity.ok(identityLinkDto);
    }

    @Override
    public ResponseEntity<List<IdentityLinkDto>> getIdentityLinksByUser(Long id) {
        return ResponseEntity.ok(
                identityLinkApiMapper.domainListToApiList(identityLinkUseCase.getIdentityLinksByUserId(id))
        );
    }
}
