package br.com.fiap.restauranteapi.application.ports.outbound.token;

import br.com.fiap.restauranteapi.application.domain.user.User;

public interface TokenGateway {
    String generate(User user);

    String getSubjectByToken(String token);
}