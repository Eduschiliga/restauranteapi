package br.com.fiap.restaurant.domain.address;

import java.time.LocalDateTime;

public class Address {
    private AddressId addressId;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;

    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Address(
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
        this.addressId = addressId;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Address with(
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
        return new Address(
                addressId,
                street,
                number,
                complement,
                city,
                state,
                zipCode,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Address newAddress(
            String street,
            String number,
            String complement,
            String city,
            String state,
            String zipCode
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new Address(
                new AddressId(null),
                street,
                number,
                complement,
                city,
                state,
                zipCode,
                true,
                now,
                now,
                null
        );
    }

    public Address update(
            String street,
            String number,
            String complement,
            String city,
            String state,
            String zipCode,
            Boolean active
    ) {
        if (Boolean.TRUE.equals(active)) {
            activate();
        } else {
            deactivate();
        }

        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public void activate() {
        this.active = true;
        this.deletedAt = null;
    }

    public void deactivate() {
        if (this.deletedAt == null) {
            this.deletedAt = LocalDateTime.now();
        }

        this.active = false;
    }

    public AddressId getAddressId() {
        return addressId;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }
}
