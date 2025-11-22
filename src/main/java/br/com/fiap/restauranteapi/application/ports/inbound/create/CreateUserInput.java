package br.com.fiap.restauranteapi.application.ports.inbound.create;

import br.com.fiap.restauranteapi.application.domain.address.Address;

public record CreateUserInput(
        String name,
        String login,
        String email,
        String password,
        Address address,
        Boolean active
) {
}
