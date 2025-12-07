package br.com.fiap.restauranteapi.application.service;

import br.com.fiap.restauranteapi.application.domain.exceptions.TokenInvalidoException;
import br.com.fiap.restauranteapi.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restauranteapi.application.domain.exceptions.UsuarioOuSenhaInvalidoException;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.*;
import br.com.fiap.restauranteapi.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restauranteapi.application.ports.outbound.token.TokenGateway;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements
        ForAuthenticatingUser,
        ForGettingUserByToken
{
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGateway tokenGateway;

    public AuthService(UserRepository userRepository,
                       PasswordEncoderPort passwordEncoder,
                       TokenGateway tokenGateway) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGateway = tokenGateway;
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
