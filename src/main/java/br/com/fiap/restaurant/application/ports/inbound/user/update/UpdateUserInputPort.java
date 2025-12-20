package br.com.fiap.restaurant.application.ports.inbound.user.update;

import br.com.fiap.restaurant.application.ports.inbound.user.update.user.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.update.user.UpdateUserOutput;

public interface UpdateUserInputPort {

    UpdateUserOutput updateUser(UpdateUserInput input);
}
