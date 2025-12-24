package br.com.fiap.restaurant.application.domain.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message, null, true, false);
    }
}
