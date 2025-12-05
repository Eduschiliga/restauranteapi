package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest;

import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.ForCreatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.ForGettingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ForListingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.ForUpdatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.user.UpdateUserOutput;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.mapper.UserMapper;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.UserDTO;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.create.CreateUserDTO;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.update.UpdateUserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping
@RestController("api/users")
public class UserController {
    private final ForCreatingUser forCreatingUser;
    private final ForUpdatingUser forUpdatingUser;
    private final ForDeletingUserById forDeletingUserById;
    private final ForListingUser forListingUser;
    private final ForGettingUserById forGettingUserById;
    private final UserMapper userMapper;

    public UserController(
            ForCreatingUser forCreatingUser,
            ForUpdatingUser forUpdatingUser,
            ForDeletingUserById forDeletingUserById,
            ForListingUser forListingUser,
            ForGettingUserById forGettingUserById,
            UserMapper userMapper
    ) {
        this.forCreatingUser = forCreatingUser;
        this.forUpdatingUser = forUpdatingUser;
        this.forDeletingUserById = forDeletingUserById;
        this.forListingUser = forListingUser;
        this.forGettingUserById = forGettingUserById;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody CreateUserDTO userDTO
    ) {
        CreateUserInput useCaseInput = userMapper.fromDTO(userDTO);
        CreateUserOutput useCaseOutput = forCreatingUser.create(useCaseInput);

        URI uri = URI.create("/users/" + useCaseOutput.userId());
        return ResponseEntity.created(uri).body(userMapper.toDTO(useCaseOutput));
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UpdateUserDTO userDTO
    ) {
        UpdateUserInput useCaseInput = userMapper.fromUpdateDTO(userDTO);
        UpdateUserOutput useCaseOutput = forUpdatingUser.updateUser(useCaseInput);

        return ResponseEntity.ok(userMapper.toDTO(useCaseOutput));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID userId) {
        forDeletingUserById.deleteUserById(userId.toString());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID userId) {
        GetUserByIdOutput useCaseOutput = forGettingUserById.getUserById(userId.toString());
        UserDTO userDTO = userMapper.toDTO(useCaseOutput);

        return ResponseEntity.ok().body(userDTO);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> listUsers() {
        List<ListUserOutput> userOutputList = forListingUser.listUers();
        List<UserDTO> userList = userMapper.toDTO(userOutputList);
        return ResponseEntity.ok().body(userList);
    }

}
