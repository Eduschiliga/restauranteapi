package br.com.fiap.restauranteapi.application.ports.inbound.create;

import br.com.fiap.restauranteapi.application.domain.address.Address;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;

import java.time.LocalDateTime;

public record CreateUserOutput(
        UserId userId,
        String name,
        String email,
        String login,
        String password,
        Address address,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public static CreateUserOutput from(User user) {
        return new CreateUserOutput(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getAddress(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }
}
