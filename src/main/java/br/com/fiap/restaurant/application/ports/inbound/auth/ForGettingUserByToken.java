package br.com.fiap.restaurant.application.ports.inbound.auth;

public interface ForGettingUserByToken {

    GetUserByTokenOutput getUserByToken(String token);
}
