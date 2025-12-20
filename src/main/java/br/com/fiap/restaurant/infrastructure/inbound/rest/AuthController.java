package br.com.fiap.restaurant.infrastructure.inbound.rest;

import br.com.fiap.restaurant.api.AuthApi;
import br.com.fiap.restaurant.application.ports.inbound.auth.ForAuthenticateUser;
import br.com.fiap.restaurant.application.ports.inbound.auth.input.AuthenticateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.auth.output.AuthenticateUserOutput;
import br.com.fiap.restaurant.model.LoginDTO;
import br.com.fiap.restaurant.model.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final ForAuthenticateUser forAuthenticateUser;

    public AuthController(ForAuthenticateUser forAuthenticateUser) {
        this.forAuthenticateUser = forAuthenticateUser;
    }

    @Override
    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {
        AuthenticateUserInput input = new AuthenticateUserInput(loginDTO.getLogin(), loginDTO.getPassword());

        AuthenticateUserOutput output = forAuthenticateUser.login(input);

        TokenDTO tokenResponse = new TokenDTO();
        tokenResponse.setToken(output.token());

        return ResponseEntity.ok().body(tokenResponse);
    }
}
