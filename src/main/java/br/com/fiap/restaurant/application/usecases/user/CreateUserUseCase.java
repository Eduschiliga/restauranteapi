package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.domain.address.Address;
import br.com.fiap.restaurant.domain.user.User;
import br.com.fiap.restaurant.application.ports.inbound.user.create.ForCreatingUser;
import br.com.fiap.restaurant.application.ports.inbound.user.create.user.CreateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.create.user.CreateUserOutput;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.Objects;

@Named
public class CreateUserUseCase implements ForCreatingUser {
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoder;

    @Inject
    public CreateUserUseCase(final UserRepositoryPort userRepositoryPort, final PasswordEncoderPort passwordEncoder) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
    }

    @Override
    @Transactional
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
                address,
                createUserInput.userType()
        );

        User createdUser = userRepositoryPort.create(newUser);

        return CreateUserOutput.from(createdUser);
    }
}
