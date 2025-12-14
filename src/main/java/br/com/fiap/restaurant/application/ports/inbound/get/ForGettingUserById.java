package br.com.fiap.restaurant.application.ports.inbound.get;

public interface ForGettingUserById {

    GetUserByIdOutput findUserById(String userId);
}
