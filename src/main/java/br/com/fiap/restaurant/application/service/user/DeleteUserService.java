package br.com.fiap.restaurant.application.service.user;

import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class DeleteUserService implements ForDeletingUserById {
    private final UserRepository userRepository;

    @Inject
    public DeleteUserService(final UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public void deleteUserById(String inputId) {
        UserId userId = UserId.from(inputId);
        userRepository.deleteById(userId);
    }
}
