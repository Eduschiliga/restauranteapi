package br.com.fiap.restauranteapi.application.domain.exceptions;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String message) {
        super(message, null, true, false);
    }
}
