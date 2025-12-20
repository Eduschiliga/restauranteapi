package br.com.fiap.restaurant.application.ports.inbound.user.password.input;


public record UpdatePasswordInput(
        String userId,
        String newPassword,
        String oldPassword
) {
}
