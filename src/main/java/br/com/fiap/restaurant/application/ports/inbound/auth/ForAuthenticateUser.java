package br.com.fiap.restaurant.application.ports.inbound.auth;

import br.com.fiap.restaurant.application.ports.inbound.auth.input.AuthenticateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.auth.output.AuthenticateUserOutput;

public interface ForAuthenticateUser {
    AuthenticateUserOutput login(AuthenticateUserInput input);
}
