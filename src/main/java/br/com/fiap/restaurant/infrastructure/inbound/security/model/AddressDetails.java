package br.com.fiap.restaurant.infrastructure.inbound.security.model;

import java.time.LocalDateTime;

public record AddressDetails(
        String addressId,
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

}