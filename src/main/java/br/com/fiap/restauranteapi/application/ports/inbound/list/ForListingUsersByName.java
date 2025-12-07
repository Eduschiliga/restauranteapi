package br.com.fiap.restauranteapi.application.ports.inbound.list;

import java.util.List;

public interface ForListingUsersByName {
    List<ListUsersByNameOutput> findAllByName(String name);
}
