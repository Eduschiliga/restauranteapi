package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.update;


import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.create.CreateAddressDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class UpdateUserDTO {
    private UUID userId;
    private String name;
    private String email;
    private String login;
    private String password;
    private UpdateAddressDTO address;
    private Boolean active;
    public UpdateUserDTO(
            UUID userId,
            String name,
            String email,
            String login,
            String password,
            UpdateAddressDTO address,
            Boolean active
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.active = active;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UpdateAddressDTO getAddress() {
        return address;
    }

    public void setAddress(UpdateAddressDTO address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }


}
