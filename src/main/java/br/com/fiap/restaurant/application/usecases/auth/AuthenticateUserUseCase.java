package br.com.fiap.restaurant.application.usecases.auth;

import br.com.fiap.restaurant.application.ports.inbound.auth.input.AuthenticateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.auth.output.AuthenticateUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.auth.output.GetUserByTokenOutput;
import br.com.fiap.restaurant.application.ports.inbound.auth.ForAuthenticateUser;
import br.com.fiap.restaurant.application.ports.inbound.auth.ForGettingUserByToken;
import br.com.fiap.restaurant.application.domain.exceptions.TokenInvalidoException;
import br.com.fiap.restaurant.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restaurant.application.domain.exceptions.UsuarioOuSenhaInvalidoException;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import br.com.fiap.restaurant.application.ports.outbound.token.TokenGeneratorPort;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class AuthenticateUserUseCase implements
        ForAuthenticateUser,
        ForGettingUserByToken
{
    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGeneratorPort tokenGeneratorPort;

    @Inject
    public AuthenticateUserUseCase(
            final UserRepositoryPort userRepositoryPort,
            final PasswordEncoderPort passwordEncoder,
            final TokenGeneratorPort tokenGeneratorPort
    ) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.tokenGeneratorPort = Objects.requireNonNull(tokenGeneratorPort);
    }

    @Override
    public AuthenticateUserOutput login(AuthenticateUserInput input) {
        User user = userRepositoryPort.findByLogin(input.login())
                .orElseThrow(() -> new UsuarioOuSenhaInvalidoException("Invalid username or password"));

        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new UsuarioOuSenhaInvalidoException("Invalid username or password");
        }

        String token = tokenGeneratorPort.generate(user);

        return new AuthenticateUserOutput(token);
    }


    @Override
    public GetUserByTokenOutput getUserByToken(String token) {
        String login = tokenGeneratorPort.getSubjectByToken(replateBearer(token));

        if (login == null || login.isEmpty()) {
            throw new TokenInvalidoException("Token inválido");
        }

        User user = userRepositoryPort.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o token informado"));

        return GetUserByTokenOutput.from(user);
    }

    private String replateBearer(String token) {
        return token.replace("Bearer ", "");
    }
}
