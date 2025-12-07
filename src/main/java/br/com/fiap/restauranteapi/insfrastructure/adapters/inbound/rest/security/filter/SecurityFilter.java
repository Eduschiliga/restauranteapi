package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.filter;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.ForGettingUserByToken;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.GetUserByTokenOutput;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.model.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final ForGettingUserByToken forGettingUserByToken;

    public SecurityFilter(ForGettingUserByToken forGettingUserByToken) {
        this.forGettingUserByToken = forGettingUserByToken;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null && !token.isBlank()) {
            GetUserByTokenOutput output = forGettingUserByToken.getUserByToken(token);

            if (output != null) {
                User userDomain = User.with(
                        output.userId(),
                        output.name(),
                        output.email(),
                        output.login(),
                        output.password(),
                        output.address(),
                        output.userType(),
                        output.active(),
                        output.createdAt(),
                        output.updatedAt(),
                        output.deletedAt()
                );

                CustomUserDetails userDetails = new CustomUserDetails(userDomain);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
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