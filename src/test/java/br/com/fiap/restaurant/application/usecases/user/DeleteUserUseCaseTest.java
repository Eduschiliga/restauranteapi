package br.com.fiap.restaurant.application.usecases.user;

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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private FindUserUseCase findUserUseCase;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    @Test
    void shouldDeleteUserSuccessfully() {
        String idStr = UUID.randomUUID().toString();
        User user = User.with(new UserId(idStr), "John", "john@test.com", "john", "pass", null, UserType.CLIENT, true, null, null, null);

        when(findUserUseCase.findUserDomainById(idStr)).thenReturn(user);

        deleteUserUseCase.deleteUserById(idStr);

        verify(userRepositoryPort, times(1)).deleteById(any(UserId.class));
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentUser() {
        String userId = UUID.randomUUID().toString();

        when(findUserUseCase.findUserDomainById(userId)).thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> deleteUserUseCase.deleteUserById(userId));
    }

    @Test
    void shouldThrowExceptionWhenDeletingWithInvalidId() {
        String invalidId = "invalid";

        when(findUserUseCase.findUserDomainById(invalidId)).thenThrow(new IllegalArgumentException("Invalid User Id"));

        assertThrows(IllegalArgumentException.class, () -> deleteUserUseCase.deleteUserById(invalidId));
    }
}