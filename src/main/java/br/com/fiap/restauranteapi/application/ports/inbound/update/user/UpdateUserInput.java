package br.com.fiap.restauranteapi.application.ports.inbound.update.user;

import br.com.fiap.restauranteapi.application.domain.user.UserType;
import br.com.fiap.restauranteapi.application.ports.inbound.update.address.UpdateAddressInput;

public record UpdateUserInput(
        String userId,
        String name,
        String login,
        String email,
        String password,
        UpdateAddressInput address,
        Boolean active,
        UserType userType
) {

}
