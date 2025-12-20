package br.com.fiap.restaurant.application.ports.inbound.user.list.output;

import br.com.fiap.restaurant.domain.address.Address;
import br.com.fiap.restaurant.domain.user.User;
import br.com.fiap.restaurant.domain.user.UserId;
import br.com.fiap.restaurant.domain.user.UserType;

import java.time.LocalDateTime;

public record ListUserOutput (
        UserId userId,
        String name,
        String email,
        String login,
        String password,
        Address address,
        Boolean active,
        UserType userType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {

public static ListUserOutput from(User user) {
    return new ListUserOutput(
            user.getUserId(),
            user.getName(),
            user.getEmail(),
            user.getLogin(),
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