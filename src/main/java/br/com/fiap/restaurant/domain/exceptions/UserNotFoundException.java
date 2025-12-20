package br.com.fiap.restaurant.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message, null, true, false);
    }
}
