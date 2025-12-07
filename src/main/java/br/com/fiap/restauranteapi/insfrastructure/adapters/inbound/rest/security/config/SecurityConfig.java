package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.config;

import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.exceptions.handler.GlobalExceptionHandler;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.filter.SecurityFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SecurityFilter securityFilter;
    private final HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(
            SecurityFilter securityFilter,
            @Qualifier("handlerExceptionResolver")HandlerExceptionResolver exceptionResolver
    ) {
        this.securityFilter = securityFilter;
        this.exceptionResolver = exceptionResolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorize) -> {
                            authorize
                                    .requestMatchers(
                                            "/api/auth/**",
                                            "/swagger-ui/**",
                                            "/v3/api-docs/**",
                                            "/swagger-resources/**",
                                            "/swagger-ui.html"
                                    ).permitAll()
                                    .requestMatchers(HttpMethod.POST, "/swagger-ui").permitAll()

                                    .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->
                                exceptionResolver.resolveException(request, response, null, authException)
                        )
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                exceptionResolver.resolveException(request, response, null, accessDeniedException)
                        )
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Necessário para H2 Console
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
