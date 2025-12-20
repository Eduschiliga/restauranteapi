package br.com.fiap.restaurant.application.ports.inbound.user.list;

import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUserOutput;

import java.util.List;

public interface ForListingUser {

    List<ListUserOutput> listUsers();

}
