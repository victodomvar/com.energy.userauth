package com.energy.userauth.infrastructure.persistence;

import com.energy.userauth.application.mapper.UserMapper;
import com.energy.userauth.domain.model.User;
import com.energy.userauth.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private UserMapper userMapper;
    @Mock
    private JpaUserRepository jpaUserRepository;

    @InjectMocks
    private UserRepositoryAdapter userRepositoryAdapter;

    @Test
    void save_persistsDomainUser() {
        User domainUser = new User(1L, "john", "john@example.com", "secret");
        UserEntity entity = new UserEntity(1L, "john", "john@example.com", "secret");

        when(userMapper.domainToEntity(domainUser)).thenReturn(entity);
        when(jpaUserRepository.save(entity)).thenReturn(entity);
        when(userMapper.entityToDomain(entity)).thenReturn(domainUser);

        User result = userRepositoryAdapter.save(domainUser);

        assertThat(result).isEqualTo(domainUser);
        verify(jpaUserRepository).save(entity);
    }

    @Test
    void findById_returnsMappedDomainUser() {
        User domainUser = new User(1L, "john", "john@example.com", "secret");
        UserEntity entity = new UserEntity(1L, "john", "john@example.com", "secret");

        when(jpaUserRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userMapper.entityToDomain(entity)).thenReturn(domainUser);

        Optional<User> result = userRepositoryAdapter.findById(1L);

        assertThat(result).contains(domainUser);
    }

    @Test
    void findByEmail_returnsMappedDomainUser() {
        User domainUser = new User(1L, "john", "john@example.com", "secret");
        UserEntity entity = new UserEntity(1L, "john", "john@example.com", "secret");

        when(jpaUserRepository.findByEmail("john@example.com")).thenReturn(Optional.of(entity));
        when(userMapper.entityToDomain(entity)).thenReturn(domainUser);

        Optional<User> result = userRepositoryAdapter.findByEmail("john@example.com");

        assertThat(result).contains(domainUser);
    }

    @Test
    void findAll_returnsMappedList() {
        User domainUser = new User(1L, "john", "john@example.com", "secret");
        List<UserEntity> entities = List.of(new UserEntity(1L, "john", "john@example.com", "secret"));
        List<User> domainUsers = List.of(domainUser);

        when(jpaUserRepository.findAll()).thenReturn(entities);
        when(userMapper.entityToApiDtoList(entities)).thenReturn(domainUsers);

        List<User> result = userRepositoryAdapter.findAll();

        assertThat(result).containsExactlyElementsOf(domainUsers);
        verify(jpaUserRepository).findAll();
    }
}
