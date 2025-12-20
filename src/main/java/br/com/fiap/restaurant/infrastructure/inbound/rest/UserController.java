package br.com.fiap.restaurant.infrastructure.inbound.rest;

import br.com.fiap.restaurant.api.UsersApi;
import br.com.fiap.restaurant.application.ports.inbound.user.create.ForCreatingUser;
import br.com.fiap.restaurant.application.ports.inbound.user.create.user.CreateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.create.user.CreateUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.delete.ForDeletingUserById;
import br.com.fiap.restaurant.application.ports.inbound.user.get.ForGettingUserById;
import br.com.fiap.restaurant.application.ports.inbound.user.get.output.GetUserByIdOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.ForListingUser;
import br.com.fiap.restaurant.application.ports.inbound.user.list.ForListingUsersByName;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUsersByNameOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.password.ForUpdatingPassword;
import br.com.fiap.restaurant.application.ports.inbound.user.password.input.UpdatePasswordInput;
import br.com.fiap.restaurant.application.ports.inbound.user.password.output.UpdatePasswordOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.update.UpdateUserInputPort;
import br.com.fiap.restaurant.application.ports.inbound.user.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.update.user.UpdateUserOutput;
import br.com.fiap.restaurant.infrastructure.inbound.rest.mapper.UserMapper;
import br.com.fiap.restaurant.infrastructure.inbound.security.model.UserDetailsImpl;
import br.com.fiap.restaurant.model.CreateUserDTO;
import br.com.fiap.restaurant.model.UpdatePasswordDTO;
import br.com.fiap.restaurant.model.UpdateUserDTO;
import br.com.fiap.restaurant.model.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController implements UsersApi {
    private final ForCreatingUser forCreatingUser;
    private final UpdateUserInputPort updateUserInputPort;
    private final ForDeletingUserById forDeletingUserById;
    private final ForListingUser forListingUser;
    private final ForListingUsersByName forListingUsersByName;
    private final ForGettingUserById forGettingUserById;
    private final ForUpdatingPassword forUpdatingPassword;

    private final UserMapper userMapper;

    public UserController(
            ForCreatingUser forCreatingUser,
            UpdateUserInputPort updateUserInputPort,
            ForDeletingUserById forDeletingUserById,
            ForListingUser forListingUser,
            ForGettingUserById forGettingUserById,
            ForListingUsersByName forListingUsersByName,
            ForUpdatingPassword forUpdatingPassword,
            UserMapper userMapper
    ) {
        this.forCreatingUser = forCreatingUser;
        this.updateUserInputPort = updateUserInputPort;
        this.forDeletingUserById = forDeletingUserById;
        this.forListingUser = forListingUser;
        this.forGettingUserById = forGettingUserById;
        this.forListingUsersByName = forListingUsersByName;
        this.forUpdatingPassword = forUpdatingPassword;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        CreateUserInput useCaseInput = userMapper.fromDTO(createUserDTO);
        CreateUserOutput useCaseOutput = forCreatingUser.create(useCaseInput);

        URI uri = URI.create("/users/" + useCaseOutput.userId());
        return ResponseEntity.created(uri).body(userMapper.toDTO(useCaseOutput));
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO userDTO) {
        String userId = getAuthenticatedUserId();

        UpdateUserInput useCaseInput = userMapper.fromUpdateDTO(userDTO, userId);
        UpdateUserOutput useCaseOutput = updateUserInputPort.updateUser(useCaseInput);

        return ResponseEntity.ok(userMapper.toDTO(useCaseOutput));
    }

    @Override
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        forDeletingUserById.deleteUserById(userId.toString());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        GetUserByIdOutput useCaseOutput = forGettingUserById.findUserById(userId.toString());

        return ResponseEntity.ok().body(userMapper.toDTO(useCaseOutput));
    }

    @Override
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<ListUserOutput> userOutputList = forListingUser.listUsers();
        List<UserDTO> userList = userMapper.toDTO(userOutputList);
        return ResponseEntity.ok().body(userList);
    }

    @Override
    public ResponseEntity<List<UserDTO>> listUsersByName(@RequestParam String name) {
        List<ListUsersByNameOutput> userOutputList = forListingUsersByName.findAllByName(name);
        List<UserDTO> userList = userMapper.toListDTO(userOutputList);

        return ResponseEntity.ok().body(userList);
    }

    @Override
    public ResponseEntity<UserDTO> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDto) {
        String userId = getAuthenticatedUserId();

        UpdatePasswordInput useCaseInput = userMapper.fromUpdatePasswordDTO(updatePasswordDto, userId);
        UpdatePasswordOutput useCaseOutput = forUpdatingPassword.updatePassword(useCaseInput);

        return ResponseEntity.ok(userMapper.toDTO(useCaseOutput));
    }

    private String getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl userDetails) {
            return userDetails.getId();
        }

        throw new IllegalStateException("Usuário não autenticado.");
    }
}
