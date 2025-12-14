package br.com.fiap.restaurant.application.ports.inbound.password;


public record UpdatePasswordInput(
        String userId,
        String newPassword,
        String oldPassword
) {
}
