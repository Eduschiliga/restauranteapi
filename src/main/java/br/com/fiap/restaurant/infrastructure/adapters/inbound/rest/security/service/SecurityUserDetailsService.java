package br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.service;

import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.model.AddressDetails;
import br.com.fiap.restaurant.infrastructure.adapters.inbound.rest.security.model.UserDetailsImpl;
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

        return new UserDetailsImpl(
                user.getUserId().value(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                mapAddress(user.getAddress()),
                user.getUserType(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }

    private AddressDetails mapAddress(br.com.fiap.restaurant.application.domain.address.Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDetails(
                address.getAddressId() != null ? address.getAddressId().value() : null,
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getActive(),
                address.getCreatedAt(),
                address.getUpdatedAt(),
                address.getDeletedAt()
        );
    }
}