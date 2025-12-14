package br.com.fiap.restaurant.infrastructure.adapters.inbound.rest;

import br.com.fiap.restaurant.api.AuthApi;
import br.com.fiap.restaurant.application.ports.inbound.auth.ForAuthenticatingUser;
import br.com.fiap.restaurant.application.ports.inbound.auth.LoginInput;
import br.com.fiap.restaurant.application.ports.inbound.auth.LoginOutput;
import br.com.fiap.restaurant.model.LoginDTO;
import br.com.fiap.restaurant.model.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final ForAuthenticatingUser forAuthenticatingUser;

    public AuthController(ForAuthenticatingUser forAuthenticatingUser) {
        this.forAuthenticatingUser = forAuthenticatingUser;
    }

    @Override
    public ResponseEntity<TokenDTO> login(LoginDTO loginDTO) {
        LoginInput input = new LoginInput(loginDTO.getLogin(), loginDTO.getPassword());

        LoginOutput output = forAuthenticatingUser.login(input);

        TokenDTO tokenResponse = new TokenDTO();
        tokenResponse.setToken(output.token());

        return ResponseEntity.ok().body(tokenResponse);
    }
}
