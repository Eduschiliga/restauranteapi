package br.com.fiap.restaurant.application.ports.inbound.user.list;

import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUsersByNameOutput;

import java.util.List;

public interface ForListingUsersByName {
    List<ListUsersByNameOutput> findAllByName(String name);
}
