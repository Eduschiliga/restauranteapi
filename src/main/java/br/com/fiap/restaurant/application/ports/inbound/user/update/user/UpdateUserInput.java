package br.com.fiap.restaurant.application.ports.inbound.user.update.user;

import br.com.fiap.restaurant.domain.user.UserType;
import br.com.fiap.restaurant.application.ports.inbound.user.update.address.UpdateAddressInput;

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
