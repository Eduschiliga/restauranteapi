package br.com.fiap.restaurant.application.domain.exceptions;

public class InvalidUserNameException extends RuntimeException {
    public InvalidUserNameException(String message) {
        super(message, null, true, false);
    }
}
