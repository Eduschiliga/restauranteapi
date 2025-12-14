package br.com.fiap.restaurant.application.service.user;

import br.com.fiap.restaurant.application.domain.address.Address;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.inbound.update.ForUpdatingUser;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserOutput;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class UpdateUserService implements ForUpdatingUser {

    private final UserRepository userRepository;
    private final FindUserService findUserService;

    @Inject
    public UpdateUserService(final UserRepository userRepository, FindUserService findUserService) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.findUserService = Objects.requireNonNull(findUserService);
    }

    @Override
    public UpdateUserOutput updateUser(UpdateUserInput input) {
        User user = findUserService.findUserDomainById(input.userId());

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
                address,
                input.active()
        );

        user = userRepository.update(user);

        return UpdateUserOutput.from(user);
    }
}
