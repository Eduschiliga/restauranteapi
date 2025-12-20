package br.com.fiap.restaurant.application.ports.inbound.user.create.address;

public record CreateAddressInput(
        String street,
        String number,
        String complement,
        String city,
        String state,
        String zipCode
) {
}
