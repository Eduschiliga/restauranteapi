package br.com.fiap.restauranteapi.application.ports.inbound.password;


public record UpdatePasswordInput(
        String userId,
        String newPassword,
        String oldPassword
) {
}
