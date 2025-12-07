package br.com.fiap.restauranteapi.application.ports.inbound.create.user;


import br.com.fiap.restauranteapi.application.domain.user.UserType;
import br.com.fiap.restauranteapi.application.ports.inbound.create.address.CreateAddressInput;

public record CreateUserInput(
        String name,
        String login,
        String email,
        String password,
        CreateAddressInput address,
        UserType userType
) {
}
