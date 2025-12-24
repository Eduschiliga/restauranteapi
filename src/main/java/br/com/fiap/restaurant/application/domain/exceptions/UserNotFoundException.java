package br.com.fiap.restaurant.application.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message, null, true, false);
    }
}
