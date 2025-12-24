package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.application.ports.inbound.user.get.output.GetUserByIdOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUsersByNameOutput;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import br.com.fiap.restaurant.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @InjectMocks
    private FindUserUseCase findUserUseCase;

    @Test
    void shouldFindUserByIdSuccessfully() {
        String idStr = UUID.randomUUID().toString();
        User user = User.with(new UserId(idStr), "John", "john@test.com", "john", "pass", null, UserType.CLIENT, true, null, null, null);

        when(userRepositoryPort.findById(any(UserId.class))).thenReturn(Optional.of(user));

        GetUserByIdOutput output = findUserUseCase.findUserById(idStr);

        assertNotNull(output);
        assertEquals(idStr, output.userId().value());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        String idStr = UUID.randomUUID().toString();
        when(userRepositoryPort.findById(any(UserId.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> findUserUseCase.findUserById(idStr));
    }

    @Test
    void shouldListUsersSuccessfully() {
        User user = User.with(new UserId("1"), "John", "john@test.com", "john", "pass", null, UserType.CLIENT, true, null, null, null);
        when(userRepositoryPort.findAll()).thenReturn(List.of(user));

        List<ListUserOutput> result = findUserUseCase.listUsers();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowExceptionWhenUserIdIsInvalidFormat() {
        String invalidId = "invalid-uuid-format";

        assertThrows(IllegalArgumentException.class, () -> findUserUseCase.findUserById(invalidId));
    }

    @Test
    void shouldThrowExceptionWhenUserDomainByIdNotFound() {
        String idStr = UUID.randomUUID().toString();

        when(userRepositoryPort.findById(any(UserId.class))).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> findUserUseCase.findUserDomainById(idStr));
    }

    @Test
    void shouldFindAllByNameSuccessfully() {
        String name = "John";
        User user = User.with(new UserId("1"), "John Doe", "john@test.com", "john", "pass", null, UserType.CLIENT, true, null, null, null);

        when(userRepositoryPort.findAllByName(name)).thenReturn(List.of(user));

        List<ListUsersByNameOutput> result = findUserUseCase.findAllByName(name);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("John Doe", result.getFirst().name());
        verify(userRepositoryPort).findAllByName(name);
    }

    @Test
    void shouldReturnEmptyListWhenNoUserFoundByName() {
        String name = "NonExistent";

        when(userRepositoryPort.findAllByName(name)).thenReturn(List.of());

        List<ListUsersByNameOutput> result = findUserUseCase.findAllByName(name);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepositoryPort).findAllByName(name);
    }
}