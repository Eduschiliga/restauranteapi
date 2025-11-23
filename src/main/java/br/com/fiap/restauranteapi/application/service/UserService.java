package br.com.fiap.restauranteapi.application.service;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.ports.inbound.create.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.CreateUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.ForCreatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.ForGettingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ForListingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.ForUpdatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.update.UpdateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.UpdateUserOutput;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService implements
        ForCreatingUser,
        ForUpdatingUser,
        ForDeletingUserById,
        ForGettingUserById,
        ForListingUser {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public CreateUserOutput create(CreateUserInput createUserInput) {

        User newUser = User.newUser(
                createUserInput.name(),
                createUserInput.email(),
                createUserInput.login(),
                createUserInput.password(),
                createUserInput.address()
        );

        User createdUser = userRepository.create(newUser);

        return CreateUserOutput.from(createdUser);
    }

    @Override
    public void deleteUserById(String inputId) {
        UserId userId = UserId.from(inputId);
        userRepository.deleteById(userId);
    }

    @Override
    public GetUserByIdOutput getUserById(String inputId) {
        User user = findUserByIdDomain(inputId);

        return GetUserByIdOutput.from(user);
    }

    private User findUserByIdDomain(String inputId) {
        UserId userId = UserId.from(inputId);

        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with ID %s not found.".formatted(inputId))
        );
    }

    @Override
    public List<ListUserOutput> listUers() {
        return userRepository
                .findAll()
                .stream()
                .map(ListUserOutput::from)
                .collect(Collectors.toList());
    }

    @Override
    public UpdateUserOutput updateUser(UpdateUserInput input) {
        User user = findUserByIdDomain(input.userId());

        user.update(
                input.name(),
                input.email(),
                input.login(),
                input.password(),
                input.address(),
                input.active()
        );

        userRepository.update(user);

        return UpdateUserOutput.from(user);
    }
}
