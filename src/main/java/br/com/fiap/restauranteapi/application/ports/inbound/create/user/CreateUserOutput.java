package br.com.fiap.restauranteapi.application.ports.inbound.create.user;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.ports.inbound.create.address.CreateAddressOutput;

import java.time.LocalDateTime;

public record CreateUserOutput(
        UserId userId,
        String name,
        String email,
        String login,
        String password,
        CreateAddressOutput address,
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
                user.getAddress() != null ?CreateAddressOutput.from(user.getAddress()) : null,
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }
}
