package br.com.fiap.restaurant.application.service;

import br.com.fiap.restaurant.application.domain.exceptions.TokenInvalidoException;
import br.com.fiap.restaurant.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restaurant.application.domain.exceptions.UsuarioOuSenhaInvalidoException;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.inbound.auth.*;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restaurant.application.ports.outbound.token.TokenGateway;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Objects;

@Named
public class AuthService implements
        ForAuthenticatingUser,
        ForGettingUserByToken
{
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGateway tokenGateway;

    @Inject
    public AuthService(
            final UserRepository userRepository,
            final PasswordEncoderPort passwordEncoder,
            final TokenGateway tokenGateway
    ) {
        this.userRepository = Objects.requireNonNull(userRepository);
        this.passwordEncoder = Objects.requireNonNull(passwordEncoder);
        this.tokenGateway = Objects.requireNonNull(tokenGateway);
    }

    @Override
    public LoginOutput login(LoginInput input) {
        User user = userRepository.findByLogin(input.login())
                .orElseThrow(() -> new UsuarioOuSenhaInvalidoException("Usuário ou senha inválidos"));

        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new UsuarioOuSenhaInvalidoException("Usuário ou senha inválidos");
        }

        String token = tokenGateway.generate(user);

        return new LoginOutput(token);
    }


    @Override
    public GetUserByTokenOutput getUserByToken(String token) {
        String login = tokenGateway.getSubjectByToken(replateBearer(token));

        if (login == null || login.isEmpty()) {
            throw new TokenInvalidoException("Token inválido");
        }

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o token informado"));

        return GetUserByTokenOutput.from(user);
    }

    private String replateBearer(String token) {
        return token.replace("Bearer ", "");
    }
}
