package br.com.fiap.restauranteapi.application.ports.inbound.update.address;

import br.com.fiap.restauranteapi.application.domain.address.AddressId;

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
