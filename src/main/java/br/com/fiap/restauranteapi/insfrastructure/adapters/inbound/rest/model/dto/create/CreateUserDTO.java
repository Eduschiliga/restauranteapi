package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.create;


import br.com.fiap.restauranteapi.application.domain.user.UserType;

import java.time.LocalDateTime;

public class CreateUserDTO {
    private String name;
    private String email;
    private String login;
    private String password;
    private CreateAddressDTO address;
    private UserType userType;
    private Boolean active;

    public CreateUserDTO(
            String name,
            String email,
            String login,
            String password,
            CreateAddressDTO address,
            UserType userType,
            Boolean active
    ) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.userType = userType;
        this.active = active;
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

    public CreateAddressDTO getAddress() {
        return address;
    }

    public void setAddress(CreateAddressDTO address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
