package br.com.fiap.restaurant.application.ports.inbound.user.update.address;

import br.com.fiap.restaurant.application.domain.address.AddressId;

public record UpdateAddressInput(
        AddressId addressId,
        String street,
        String number,
        String complement,
        String city,
        String state,
        String zipCode,

        Boolean active
) {
}
