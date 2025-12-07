package br.com.fiap.restauranteapi.application.domain.exceptions;

public class TokenInvalidoException extends RuntimeException {
    public TokenInvalidoException(String message) {
        super(message, null, true, false);
    }
}
