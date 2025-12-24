package br.com.fiap.restaurant.application.domain.exceptions;

public class UsuarioOuSenhaInvalidoException extends RuntimeException {
    public UsuarioOuSenhaInvalidoException(String message) {
        super(message, null, true, false);
    }
}
