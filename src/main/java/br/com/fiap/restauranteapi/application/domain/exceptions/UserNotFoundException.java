package br.com.fiap.restauranteapi.application.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message, null, true, false);
    }
}
