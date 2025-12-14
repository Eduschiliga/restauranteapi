package br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.exceptions.handler;

import br.com.fiap.restaurant.application.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ProblemDetail buildProblemDetail(final RuntimeException ex, final HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ProblemDetail handleNotFound(final RuntimeException ex) {
        return buildProblemDetail(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ProblemDetail handleForbidden(final RuntimeException ex) {
        return buildProblemDetail(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(
            {
                    InvalidUserNameException.class,
                    UsuarioOuSenhaInvalidoException.class,
                    TokenInvalidoException.class,
                    InvalidPasswordException.class
            }
    )
    protected ProblemDetail handleBadRequest(final RuntimeException ex) {
        return buildProblemDetail(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ProblemDetail handleAuthenticationException(final AuthenticationException ex) {
        return buildProblemDetail(ex, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    protected ProblemDetail handleGeneralException(final RuntimeException ex) {
        return buildProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
