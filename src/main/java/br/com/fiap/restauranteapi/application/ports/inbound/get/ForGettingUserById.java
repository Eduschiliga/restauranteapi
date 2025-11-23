package br.com.fiap.restauranteapi.application.ports.inbound.get;

public interface ForGettingUserById {

    GetUserByIdOutput getUserById(String userId);
}
