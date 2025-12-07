package br.com.fiap.restauranteapi.application.domain.exceptions;

public class UsuarioOuSenhaInvalidoException extends RuntimeException {
    public UsuarioOuSenhaInvalidoException(String message) {
        super(message, null, true, false);
    }
}
