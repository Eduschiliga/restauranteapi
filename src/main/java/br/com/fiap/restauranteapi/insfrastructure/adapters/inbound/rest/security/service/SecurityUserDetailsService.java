package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.service;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.model.CustomUserDetails; // Importe a nova classe
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new CustomUserDetails(user);
    }
}