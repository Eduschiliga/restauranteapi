package br.com.fiap.restauranteapi.application.ports.outbound.repository;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User create(User aUser);

    User update(User aUser);

    Optional<User> findById(UserId anId);
    List<User> findAllByName(String name);

    List<User> findAll();

    void deleteById(UserId anId);

    Optional<User> findByLogin(String login);
}
