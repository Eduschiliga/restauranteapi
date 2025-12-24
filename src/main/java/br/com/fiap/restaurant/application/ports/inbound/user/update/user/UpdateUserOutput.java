package br.com.fiap.restaurant.application.ports.inbound.user.update.user;

import br.com.fiap.restaurant.application.domain.address.Address;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;

import java.time.LocalDateTime;

public record UpdateUserOutput(
        UserId userId,
        String name,
        String login,
        String email,
        String password,
        Address address,
        Boolean active,
        UserType userType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public static UpdateUserOutput from(User user) {
        return new UpdateUserOutput(
                user.getUserId(),
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getPassword(),
                user.getAddress(),
                user.getActive(),
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }

}
