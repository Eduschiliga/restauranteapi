package br.com.fiap.restaurant.application.ports.inbound.user.get;

import br.com.fiap.restaurant.application.ports.inbound.user.get.output.GetUserByIdOutput;

public interface ForGettingUserById {

    GetUserByIdOutput findUserById(String userId);
}
