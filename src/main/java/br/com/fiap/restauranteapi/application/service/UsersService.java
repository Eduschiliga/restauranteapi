package br.com.fiap.restauranteapi.application.service;

import br.com.fiap.restauranteapi.application.domain.address.Address;
import br.com.fiap.restauranteapi.application.domain.exceptions.InvalidPasswordException;
import br.com.fiap.restauranteapi.application.domain.exceptions.InvalidUserNameException;
import br.com.fiap.restauranteapi.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.ports.inbound.create.ForCreatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.ForGettingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ForListingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ForListingUsersByName;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ListUsersByNameOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.password.ForUpdatingPassword;
import br.com.fiap.restauranteapi.application.ports.inbound.password.UpdatePasswordInput;
import br.com.fiap.restauranteapi.application.ports.inbound.password.UpdatePasswordOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.ForUpdatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.update.user.UpdateUserOutput;
import br.com.fiap.restauranteapi.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UsersService implements
        ForCreatingUser,
        ForUpdatingUser,
        ForDeletingUserById,
        ForGettingUserById,
        ForListingUser,
        ForListingUsersByName,
        ForUpdatingPassword
{

    private final PasswordEncoderPort passwordEncoder;
    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository, PasswordEncoderPort passwordEncoder) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
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

        String encodedPassword = passwordEncoder.encode(createUserInput.password());

        User newUser = User.newUser(
                createUserInput.name(),
                createUserInput.email(),
                createUserInput.login(),
                encodedPassword,
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
                () -> new UserNotFoundException("User with ID %s not found.".formatted(inputId))
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

    @Override
    public List<ListUsersByNameOutput> findAllByName(String name) {
        validateUserName(name);

        return userRepository
                .findAllByName(name)
                .stream()
                .map(ListUsersByNameOutput::from)
                .collect(Collectors.toList());
    }

    private void validateUserName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidUserNameException("Name cannot be null or blank.");
        }
    }

    @Override
    public UpdatePasswordOutput updatePassword(UpdatePasswordInput input) {
        User user = findUserByIdDomain(input.userId());

        String actualPassword = user.getPassword();
        boolean matchPassword = passwordEncoder.matches(input.oldPassword(), actualPassword);

        if (!matchPassword) {
            throw new InvalidPasswordException("Old password is invalid.");
        }

        if (input.newPassword() == null || input.newPassword().isBlank()) {
            throw new InvalidPasswordException("New password cannot be null or blank.");
        }

        if (passwordEncoder.matches(input.newPassword(), actualPassword)) {
            throw new InvalidPasswordException("New password cannot be the same as old password.");
        }

        user.updatePassword(passwordEncoder.encode(input.newPassword()));

        user = userRepository.update(user);

        return UpdatePasswordOutput.from(user);
    }
}
