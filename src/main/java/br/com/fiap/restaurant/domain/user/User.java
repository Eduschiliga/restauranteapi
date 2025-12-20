package br.com.fiap.restaurant.domain.user;

import br.com.fiap.restaurant.domain.address.Address;

import java.time.LocalDateTime;

public class User {
    private UserId userId;
    private String name;
    private String email;
    private String login;
    private String password;
    private Address address;
    private UserType userType;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public User(
            UserId userId,
            String name,
            String email,
            String login,
            String password,
            Address address,
            UserType userType,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.userType = userType;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static User with(
            UserId userId,
            String name,
            String email,
            String login,
            String password,
            Address address,
            UserType userType,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            LocalDateTime deletedAt
    ) {
        return new User(
                userId,
                name,
                email,
                login,
                password,
                address,
                userType,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static User newUser(
            String name,
            String email,
            String login,
            String password,
            Address address,
            UserType userType
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new User(
                new UserId(null),
                name,
                email,
                login,
                password,
                address,
                userType,
                true,
                now,
                now,
                null
        );
    }

    public User update(
            String name,
            String email,
            String login,
            Address address,
            Boolean active
    ) {
        if (Boolean.TRUE.equals(active)) {
            activate();
        } else {
            deactivate();
        }

        this.name = name;
        this.email = email;
        this.login = login;
        this.address = address;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public User updatePassword(String password) {
        this.password = password;
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

    public UserId getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
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

    public UserType getUserType() {
        return userType;
    }
}
