package br.com.fiap.restauranteapi.application.ports.inbound.create;

import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.user.CreateUserOutput;

public interface ForCreatingUser {
    CreateUserOutput create(CreateUserInput createUserInput);
}
