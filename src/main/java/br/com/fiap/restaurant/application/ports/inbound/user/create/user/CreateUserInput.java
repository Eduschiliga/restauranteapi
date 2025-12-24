package br.com.fiap.restaurant.application.ports.inbound.user.create.user;


import br.com.fiap.restaurant.application.domain.user.UserType;
import br.com.fiap.restaurant.application.ports.inbound.user.create.address.CreateAddressInput;

public record CreateUserInput(
        String name,
        String login,
        String email,
        String password,
        CreateAddressInput address,
        UserType userType
) {
}
