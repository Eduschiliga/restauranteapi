package br.com.fiap.restaurant.application.service.user;

import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class DeleteUserService implements ForDeletingUserById {
    private final UserRepository userRepository;
    private final FindUserService findUserService;

    @Inject
    public DeleteUserService(final UserRepository userRepository, FindUserService findUserService) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.findUserService = Objects.requireNonNull(findUserService);
    }

    @Override
    public void deleteUserById(String inputId) {
        User user = findUserService.findUserDomainById(inputId);

        UserId userId = user.getUserId();
        userRepository.deleteById(userId);
    }
}
