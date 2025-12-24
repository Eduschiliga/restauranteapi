package br.com.fiap.restaurant.infrastructure.inbound.rest.exceptions.handler;

import br.com.fiap.restaurant.application.domain.exceptions.*;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ProblemDetail buildProblemDetail(final RuntimeException ex, final HttpStatus status) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle(status.getReasonPhrase());
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String detail = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("timestamp", Instant.now());

        return ResponseEntity.status(status).body(problemDetail);
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
