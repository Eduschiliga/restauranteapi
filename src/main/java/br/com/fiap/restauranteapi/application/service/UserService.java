package br.com.fiap.restauranteapi.application.service;

import br.com.fiap.restauranteapi.application.domain.address.Address;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
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
        Address address = null;

        if (createUserInput.address() != null) {
            address = Address.newAddress(
                    createUserInput.address().street(),
                    createUserInput.address().number(),
                    createUserInput.address().complement(),
                    createUserInput.address().city(),
                    createUserInput.address().state(),
                    createUserInput.address().zipCode()
            );
        }

        User newUser = User.newUser(
                createUserInput.name(),
                createUserInput.email(),
                createUserInput.login(),
                createUserInput.password(),
                address
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

        Address address;
        if (user.getAddress() == null) {
            address = Address.with(
                    null,
                    input.address().street(),
                    input.address().number(),
                    input.address().complement(),
                    input.address().city(),
                    input.address().state(),
                    input.address().zipCode(),
                    input.address().active(),
                    null,
                    null,
                    null
            );
        } else {
            address = user.getAddress().update(
                    input.address().street(),
                    input.address().number(),
                    input.address().complement(),
                    input.address().city(),
                    input.address().state(),
                    input.address().zipCode(),
                    input.address().active()
            );
        }

        user.update(
                input.name(),
                input.email(),
                input.login(),
                input.password(),
                address,
                input.active()
        );

        user = userRepository.update(user);

        return UpdateUserOutput.from(user);
    }
}
