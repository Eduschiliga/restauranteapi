package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.domain.exceptions.UserNotFoundException;
import br.com.fiap.restaurant.domain.user.User;
import br.com.fiap.restaurant.domain.user.UserId;
import br.com.fiap.restaurant.application.ports.inbound.user.get.ForGettingUserById;
import br.com.fiap.restaurant.application.ports.inbound.user.get.ForGettingUserDomainById;
import br.com.fiap.restaurant.application.ports.inbound.user.get.output.GetUserByIdOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.ForListingUser;
import br.com.fiap.restaurant.application.ports.inbound.user.list.ForListingUsersByName;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUsersByNameOutput;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class FindUserUseCase implements
        ForGettingUserById,
        ForListingUser,
        ForListingUsersByName,
        ForGettingUserDomainById
{
    private final UserRepositoryPort userRepositoryPort;

    @Inject
    public FindUserUseCase(final UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort);
    }

    @Override
    public GetUserByIdOutput findUserById(String inputId) {
        User user = findUserDomainById(inputId);

        return GetUserByIdOutput.from(user);
    }

    @Override
    public User findUserDomainById(String inputId) {
        UserId userId = UserId.from(inputId);

        return userRepositoryPort.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with ID %s not found.".formatted(inputId))
        );
    }

    @Override
    public List<ListUserOutput> listUsers() {
        return userRepositoryPort
                .findAll()
                .stream()
                .map(ListUserOutput::from)
                .collect(Collectors.toList());
    }



    @Override
    public List<ListUsersByNameOutput> findAllByName(String name) {
        return userRepositoryPort
                .findAllByName(name)
                .stream()
                .map(ListUsersByNameOutput::from)
                .collect(Collectors.toList());
    }
}
