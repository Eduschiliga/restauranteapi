package br.com.fiap.restauranteapi.application.ports.inbound.update;

import br.com.fiap.restauranteapi.application.domain.address.Address;

public record UpdateUserInput(
        String userId,
        String name,
        String login,
        String email,
        String password,
        Address address,
        Boolean active
) {

}
