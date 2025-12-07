package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.filter;

import br.com.fiap.restauranteapi.application.domain.exceptions.TokenInvalidoException;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.ForAuthenticatingUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final ForAuthenticatingUser forAuthenticatingUser;

    public SecurityFilter(ForAuthenticatingUser forAuthenticatingUser) {
        this.forAuthenticatingUser = forAuthenticatingUser;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null && !token.isBlank()) {
            User user = forAuthenticatingUser.validateToken(token);

            if (user != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        new ArrayList<>()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }

        filterChain.doFilter(request, response);
    }


    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
