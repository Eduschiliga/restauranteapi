package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.application.ports.inbound.user.password.input.UpdatePasswordInput;
import br.com.fiap.restaurant.application.ports.inbound.user.password.ForUpdatingPassword;
import br.com.fiap.restaurant.application.ports.inbound.user.password.output.UpdatePasswordOutput;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import br.com.fiap.restaurant.domain.exceptions.InvalidPasswordException;
import br.com.fiap.restaurant.domain.user.User;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.Objects;

@Named
public class UpdatePasswordUseCase implements
        ForUpdatingPassword {

    private final UserRepositoryPort userRepositoryPort;
    private final FindUserUseCase findUserUseCase;
    private final PasswordEncoderPort passwordEncoder;

    @Inject
    public UpdatePasswordUseCase(
            final UserRepositoryPort userRepositoryPort,
            final PasswordEncoderPort passwordEncoder,
            final FindUserUseCase findUserUseCase
    ) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.findUserUseCase = Objects.requireNonNull(findUserUseCase);
    }

    @Override
    @Transactional
    public UpdatePasswordOutput updatePassword(UpdatePasswordInput input) {
        User user = findUserUseCase.findUserDomainById(input.userId());

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

        user = userRepositoryPort.update(user);

        return UpdatePasswordOutput.from(user);
    }
}
