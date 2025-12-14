package br.com.fiap.restaurant.application.service.user;

import br.com.fiap.restaurant.application.domain.exceptions.InvalidPasswordException;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.inbound.password.ForUpdatingPassword;
import br.com.fiap.restaurant.application.ports.inbound.password.UpdatePasswordInput;
import br.com.fiap.restaurant.application.ports.inbound.password.UpdatePasswordOutput;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UpdatePasswordUserService implements
        ForUpdatingPassword {

    private final UserRepository userRepository;
    private final FindUserService findUserService;
    private final PasswordEncoderPort passwordEncoder;

    @Inject
    public UpdatePasswordUserService(
            final UserRepository userRepository,
            final PasswordEncoderPort passwordEncoder,
            final FindUserService findUserService
    ) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.findUserService = Objects.requireNonNull(findUserService);
    }

    @Override
    public UpdatePasswordOutput updatePassword(UpdatePasswordInput input) {
        User user = findUserService.findUserDomainById(input.userId());

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
