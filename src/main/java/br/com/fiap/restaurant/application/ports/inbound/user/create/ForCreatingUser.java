package br.com.fiap.restaurant.application.ports.inbound.user.create;

import br.com.fiap.restaurant.application.ports.inbound.user.create.user.CreateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.create.user.CreateUserOutput;

public interface ForCreatingUser {
    CreateUserOutput create(CreateUserInput createUserInput);
}
