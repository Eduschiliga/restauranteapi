package br.com.fiap.restauranteapi.application.ports.inbound.update.address;

import br.com.fiap.restauranteapi.application.domain.address.Address;
import br.com.fiap.restauranteapi.application.domain.address.AddressId;

import java.time.LocalDateTime;

public record UpdateAddressOutput(
        AddressId addressId,
        String street,
        String number,
        String complement,
        String city,
        String state,
        String zipCode,

        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public static UpdateAddressOutput from(Address user) {
        return new UpdateAddressOutput(
                user.getAddressId(),
                user.getStreet(),
                user.getNumber(),
                user.getComplement(),
                user.getCity(),
                user.getState(),
                user.getZipCode(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }

}
