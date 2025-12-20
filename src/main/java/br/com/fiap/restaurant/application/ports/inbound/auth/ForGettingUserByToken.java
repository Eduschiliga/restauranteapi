package br.com.fiap.restaurant.application.ports.inbound.auth;

import br.com.fiap.restaurant.application.ports.inbound.auth.output.GetUserByTokenOutput;

public interface ForGettingUserByToken {

    GetUserByTokenOutput getUserByToken(String token);
}
