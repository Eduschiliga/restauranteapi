package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.update;

import java.util.UUID;

public class UpdateAddressDTO {
    private UUID addressId;
    private String street;
    private String number;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
    private Boolean active;

    public UpdateAddressDTO(
            UUID addressId,
            String street,
            String number,
            String complement,
            String city,
            String state,
            String zipCode,
            Boolean active
    ) {
        this.addressId = addressId;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.active = active;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
