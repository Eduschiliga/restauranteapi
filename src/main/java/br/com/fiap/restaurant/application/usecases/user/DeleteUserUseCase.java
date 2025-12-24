package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.ports.inbound.user.delete.ForDeletingUserById;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.Objects;

@Named
public class DeleteUserUseCase implements ForDeletingUserById {
    private final UserRepositoryPort userRepositoryPort;
    private final FindUserUseCase findUserUseCase;

    @Inject
    public DeleteUserUseCase(final UserRepositoryPort userRepositoryPort, FindUserUseCase findUserUseCase) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort);
        this.findUserUseCase = Objects.requireNonNull(findUserUseCase);
    }

    @Override
    @Transactional
    public void deleteUserById(String inputId) {
        User user = findUserUseCase.findUserDomainById(inputId);

        UserId userId = user.getUserId();
        userRepositoryPort.deleteById(userId);
    }
}
