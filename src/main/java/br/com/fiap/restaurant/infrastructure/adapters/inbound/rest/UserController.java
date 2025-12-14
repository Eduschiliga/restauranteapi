package br.com.fiap.restaurant.infrastructure.adapters.inbound.rest;

import br.com.fiap.restaurant.api.UsersApi;
import br.com.fiap.restaurant.application.ports.inbound.create.ForCreatingUser;
import br.com.fiap.restaurant.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.create.user.CreateUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restaurant.application.ports.inbound.get.ForGettingUserById;
import br.com.fiap.restaurant.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restaurant.application.ports.inbound.list.ForListingUser;
import br.com.fiap.restaurant.application.ports.inbound.list.ForListingUsersByName;
import br.com.fiap.restaurant.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.list.ListUsersByNameOutput;
import br.com.fiap.restaurant.application.ports.inbound.password.ForUpdatingPassword;
import br.com.fiap.restaurant.application.ports.inbound.password.UpdatePasswordInput;
import br.com.fiap.restaurant.application.ports.inbound.password.UpdatePasswordOutput;
import br.com.fiap.restaurant.application.ports.inbound.update.ForUpdatingUser;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserOutput;
import br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.mapper.UserMapper;
import br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.model.UserDetailsImpl;
import br.com.fiap.restaurant.model.CreateUserDTO;
import br.com.fiap.restaurant.model.UpdatePasswordDTO;
import br.com.fiap.restaurant.model.UpdateUserDTO;
import br.com.fiap.restaurant.model.UserDTO;
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
    private final ForUpdatingUser forUpdatingUser;
    private final ForDeletingUserById forDeletingUserById;
    private final ForListingUser forListingUser;
    private final ForListingUsersByName forListingsUsersByName;
    private final ForGettingUserById forGettingUserById;
    private final ForUpdatingPassword forUpdatingPassword;

    private final UserMapper userMapper;

    public UserController(
            ForCreatingUser forCreatingUser,
            ForUpdatingUser forUpdatingUser,
            ForDeletingUserById forDeletingUserById,
            ForListingUser forListingUser,
            ForGettingUserById forGettingUserById,
            ForListingUsersByName forListingsUsersByName,
            ForUpdatingPassword forUpdatingPassword,
            UserMapper userMapper
    ) {
        this.forCreatingUser = forCreatingUser;
        this.forUpdatingUser = forUpdatingUser;
        this.forDeletingUserById = forDeletingUserById;
        this.forListingUser = forListingUser;
        this.forGettingUserById = forGettingUserById;
        this.forListingsUsersByName = forListingsUsersByName;
        this.forUpdatingPassword = forUpdatingPassword;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<UserDTO> createUser(CreateUserDTO createUserDTO) {
        CreateUserInput useCaseInput = userMapper.fromDTO(createUserDTO);
        CreateUserOutput useCaseOutput = forCreatingUser.create(useCaseInput);

        URI uri = URI.create("/users/" + useCaseOutput.userId());
        return ResponseEntity.created(uri).body(userMapper.toDTO(useCaseOutput));
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UpdateUserDTO userDTO
    ) {
        String userId = getAuthenticatedUserId();

        UpdateUserInput useCaseInput = userMapper.fromUpdateDTO(userDTO, userId);
        UpdateUserOutput useCaseOutput = forUpdatingUser.updateUser(useCaseInput);

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
    public ResponseEntity<List<UserDTO>> listUsersByName(
            @RequestParam String name
    ) {
        List<ListUsersByNameOutput> userOutputList = forListingsUsersByName.findAllByName(name);
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
