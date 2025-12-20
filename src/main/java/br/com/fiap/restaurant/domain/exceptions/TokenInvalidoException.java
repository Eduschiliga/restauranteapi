package br.com.fiap.restaurant.domain.exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message, null, true, false);
    }
}
