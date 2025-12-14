package br.com.fiap.restaurant.application.ports.inbound.update.user;

import br.com.fiap.restaurant.application.domain.user.UserType;
import br.com.fiap.restaurant.application.ports.inbound.update.address.UpdateAddressInput;

public record UpdateUserInput(
        String userId,
        String name,
        String login,
        String email,
        UpdateAddressInput address,
        Boolean active,
        UserType userType
) {

}
