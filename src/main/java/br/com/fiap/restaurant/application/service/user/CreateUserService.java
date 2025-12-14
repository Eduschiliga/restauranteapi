package br.com.fiap.restaurant.application.service.user;

import br.com.fiap.restaurant.application.domain.address.Address;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.inbound.create.ForCreatingUser;
import br.com.fiap.restaurant.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.create.user.CreateUserOutput;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class CreateUserService implements ForCreatingUser {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;

    @Inject
    public CreateUserService(final UserRepository userRepository, final PasswordEncoderPort passwordEncoder) {
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
                address,
                createUserInput.userType()
        );

        User createdUser = userRepository.create(newUser);

        return CreateUserOutput.from(createdUser);
    }
}
