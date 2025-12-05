package br.com.fiap.restauranteapi.application.ports.inbound.update.user;

import br.com.fiap.restauranteapi.application.domain.address.Address;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;

import java.time.LocalDateTime;

public record UpdateUserOutput(
        UserId userId,
        String name,
        String login,
        String email,
        String password,
        Address address,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public static UpdateUserOutput from(User user) {
        return new UpdateUserOutput(
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
