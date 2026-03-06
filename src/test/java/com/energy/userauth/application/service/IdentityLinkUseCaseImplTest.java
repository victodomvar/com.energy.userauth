package com.energy.userauth.application.service;

import com.energy.userauth.application.port.out.IdentityLinkRepositoryPort;
import com.energy.userauth.application.port.out.UserRepositoryPort;
import com.energy.userauth.application.usecase.IdentityLinkUseCaseImpl;
import com.energy.userauth.domain.exception.IdentityLinkAlreadyExistsException;
import com.energy.userauth.domain.exception.UserNotFoundException;
import com.energy.userauth.domain.model.IdentityLink;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.domain.model.UserStatus;
import com.energy.userauth.domain.service.IdentityLinkDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdentityLinkUseCaseImplTest {

    @Mock
    private IdentityLinkRepositoryPort identityLinkRepository;
    @Mock
    private UserRepositoryPort userRepository;
    @Mock
    private IdentityLinkDomainService identityLinkDomainService;

    @InjectMocks
    private IdentityLinkUseCaseImpl identityLinkUseCase;

    private User user;
    private IdentityLink identityLink;

    @BeforeEach
    void setUp() {
        user = new User(1L, "john", "john@example.com", UserStatus.ACTIVE, null, null);
        identityLink = new IdentityLink(null, null, "https://issuer", "sub-1", "keycloak", null);
    }

    @Test
    void createIdentityLink_withValidInput_savesLink() {
        IdentityLink persisted = new IdentityLink(10L, 1L, "https://issuer", "sub-1", "keycloak", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(identityLinkRepository.findByIssuerAndSubject("https://issuer", "sub-1")).thenReturn(Optional.empty());
        when(identityLinkRepository.save(identityLink)).thenReturn(persisted);

        IdentityLink result = identityLinkUseCase.createIdentityLink(1L, identityLink);

        assertThat(result).isEqualTo(persisted);
        verify(identityLinkDomainService).validateIdentityLink(identityLink);
        verify(identityLinkRepository).save(identityLink);
    }

    @Test
    void createIdentityLink_whenUserDoesNotExist_throwsNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> identityLinkUseCase.createIdentityLink(1L, identityLink));
        verify(identityLinkRepository, never()).save(identityLink);
    }

    @Test
    void createIdentityLink_whenIssuerAndSubjectExist_throwsConflict() {
        IdentityLink existing = new IdentityLink(5L, 2L, "https://issuer", "sub-1", "keycloak", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(identityLinkRepository.findByIssuerAndSubject("https://issuer", "sub-1"))
                .thenReturn(Optional.of(existing));

        assertThrows(IdentityLinkAlreadyExistsException.class,
                () -> identityLinkUseCase.createIdentityLink(1L, identityLink));
        verify(identityLinkRepository, never()).save(identityLink);
    }

    @Test
    void getIdentityLinksByUserId_returnsLinks() {
        IdentityLink existing = new IdentityLink(5L, 1L, "https://issuer", "sub-1", "keycloak", null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(identityLinkRepository.findByUserId(1L)).thenReturn(List.of(existing));

        List<IdentityLink> result = identityLinkUseCase.getIdentityLinksByUserId(1L);

        assertThat(result).containsExactly(existing);
    }
}
