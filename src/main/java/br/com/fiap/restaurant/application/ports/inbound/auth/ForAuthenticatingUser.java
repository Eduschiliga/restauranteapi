package br.com.fiap.restaurant.application.ports.inbound.auth;

public interface ForAuthenticatingUser {
    LoginOutput login(LoginInput input);
}
