package br.com.fiap.restauranteapi.application.ports.inbound.create;

public interface ForCreatingUser {
    CreateUserOutput create(CreateUserInput createUserInput);
}
