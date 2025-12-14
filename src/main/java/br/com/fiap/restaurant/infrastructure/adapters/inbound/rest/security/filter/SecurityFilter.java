package br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.filter;

import br.com.fiap.restaurant.application.ports.inbound.auth.ForGettingUserByToken;
import br.com.fiap.restaurant.application.ports.inbound.auth.GetUserByTokenOutput;
import br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.model.AddressDetails;
import br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.model.UserDetailsImpl;
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
                UserDetailsImpl userDetails = new UserDetailsImpl(
                        output.userId().value(),
                        output.name(),
                        output.email(),
                        output.login(),
                        output.password(),
                        output.address() != null ?
                        new AddressDetails(
                                output.address().getAddressId().value(),
                                output.address().getStreet(),
                                output.address().getNumber(),
                                output.address().getComplement(),
                                output.address().getCity(),
                                output.address().getState(),
                                output.address().getZipCode(),
                                output.address().getActive(),
                                output.address().getCreatedAt(),
                                output.address().getUpdatedAt(),
                                output.address().getDeletedAt()
                        ) : null,
                        output.userType(),
                        output.active(),
                        output.createdAt(),
                        output.updatedAt(),
                        output.deletedAt()
                );

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
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}