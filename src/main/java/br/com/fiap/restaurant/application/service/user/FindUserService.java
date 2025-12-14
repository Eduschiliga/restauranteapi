package br.com.fiap.restaurant.application.service.user;

import br.com.fiap.restaurant.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.ports.inbound.get.ForGettingUserById;
import br.com.fiap.restaurant.application.ports.inbound.get.ForGettingUserDomainById;
import br.com.fiap.restaurant.application.ports.inbound.get.GetUserByIdOutput;
import br.com.fiap.restaurant.application.ports.inbound.list.ForListingUser;
import br.com.fiap.restaurant.application.ports.inbound.list.ForListingUsersByName;
import br.com.fiap.restaurant.application.ports.inbound.list.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.list.ListUsersByNameOutput;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Named
public class FindUserService implements
        ForGettingUserById,
        ForListingUser,
        ForListingUsersByName,
        ForGettingUserDomainById
{
    private final UserRepository userRepository;

    @Inject
    public FindUserService(final UserRepository userRepository) {
        this.userRepository = Objects.requireNonNull(userRepository);
    }

    @Override
    public GetUserByIdOutput findUserById(String inputId) {
        User user = findUserDomainById(inputId);

        return GetUserByIdOutput.from(user);
    }

    @Override
    public User findUserDomainById(String inputId) {
        UserId userId = UserId.from(inputId);

        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User with ID %s not found.".formatted(inputId))
        );
    }

    @Override
    public List<ListUserOutput> listUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(ListUserOutput::from)
                .collect(Collectors.toList());
    }



    @Override
    public List<ListUsersByNameOutput> findAllByName(String name) {
        return userRepository
                .findAllByName(name)
                .stream()
                .map(ListUsersByNameOutput::from)
                .collect(Collectors.toList());
    }
}
