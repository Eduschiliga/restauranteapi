package br.com.fiap.restaurant.application.ports.inbound.get;

import br.com.fiap.restaurant.application.domain.user.User;

public interface ForGettingUserDomainById {
    User findUserDomainById(String userId);
}
