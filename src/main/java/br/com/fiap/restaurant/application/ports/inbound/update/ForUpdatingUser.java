package br.com.fiap.restaurant.application.ports.inbound.update;

import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.update.user.UpdateUserOutput;

public interface ForUpdatingUser {

    UpdateUserOutput updateUser(UpdateUserInput input);
}
