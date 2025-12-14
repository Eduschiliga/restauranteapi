package br.com.fiap.restaurant.application.ports.inbound.create.address;

import br.com.fiap.restaurant.application.domain.address.Address;
import br.com.fiap.restaurant.application.domain.address.AddressId;

import java.time.LocalDateTime;

public record CreateAddressOutput(
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
    public static CreateAddressOutput from(Address address) {
        return new CreateAddressOutput(
                address.getAddressId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getActive(),
                address.getCreatedAt(),
                address.getUpdatedAt(),
                address.getDeletedAt()
        );
    }

}
