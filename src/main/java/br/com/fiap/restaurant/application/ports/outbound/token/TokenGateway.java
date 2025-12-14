package br.com.fiap.restaurant.application.ports.outbound.token;

import br.com.fiap.restaurant.application.domain.user.User;

public interface TokenGateway {
    String generate(User user);

    String getSubjectByToken(String token);
}