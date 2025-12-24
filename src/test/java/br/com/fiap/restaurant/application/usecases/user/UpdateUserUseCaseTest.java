package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.application.ports.inbound.user.update.address.UpdateAddressInput;
import br.com.fiap.restaurant.application.ports.inbound.user.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.update.user.UpdateUserOutput;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import br.com.fiap.restaurant.application.domain.address.Address;
import br.com.fiap.restaurant.application.domain.address.AddressId;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private FindUserUseCase findUserUseCase;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    @Test
    void shouldUpdateUserSuccessfully() {
        String userIdStr = UUID.randomUUID().toString();
        Address existingAddress = Address.with(new AddressId("addr-1"), "Old St", "1", null, "City", "ST", "00000", true, null, null, null);
        User existingUser = User.with(new UserId(userIdStr), "Old Name", "old@email.com", "login", "pass", existingAddress, UserType.CLIENT, true, null, null, null);

        UpdateAddressInput addrInput = new UpdateAddressInput(null, "New St", "2", "Comp", "New City", "ST", "11111", true);
        UpdateUserInput input = new UpdateUserInput(userIdStr, "New Name", "login", "new@email.com", addrInput, true, UserType.CLIENT);

        when(findUserUseCase.findUserDomainById(userIdStr)).thenReturn(existingUser);
        when(userRepositoryPort.update(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateUserOutput output = updateUserUseCase.updateUser(input);

        assertNotNull(output);
        assertEquals("New Name", output.name());
        assertEquals("new@email.com", output.email());
        assertEquals("New St", output.address().getStreet());
        verify(userRepositoryPort).update(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentUser() {
        String userIdStr = UUID.randomUUID().toString();
        UpdateUserInput input = new UpdateUserInput(userIdStr, "Name", "login", "email", null, true, UserType.CLIENT);

        when(findUserUseCase.findUserDomainById(userIdStr)).thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.updateUser(input));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingWithInvalidIdFormat() {
        String invalidId = "123";
        UpdateUserInput input = new UpdateUserInput(invalidId, "Name", "login", "email", null, true, UserType.CLIENT);

        when(findUserUseCase.findUserDomainById(invalidId)).thenThrow(new IllegalArgumentException("Invalid User Id"));

        assertThrows(IllegalArgumentException.class, () -> updateUserUseCase.updateUser(input));
    }
}