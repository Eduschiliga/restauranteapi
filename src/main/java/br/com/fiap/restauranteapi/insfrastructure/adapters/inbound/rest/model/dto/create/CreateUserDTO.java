package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.create;


import java.time.LocalDateTime;

public class CreateUserDTO {
    private String name;
    private String email;
    private String login;
    private String password;
    private CreateAddressDTO address;
    private Boolean active;

    public CreateUserDTO(
            String name,
            String email,
            String login,
            String password,
            CreateAddressDTO address,
            Boolean active
    ) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
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
}
