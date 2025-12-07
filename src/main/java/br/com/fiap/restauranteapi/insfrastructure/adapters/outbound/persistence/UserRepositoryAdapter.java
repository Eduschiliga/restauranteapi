package br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence.entity.UserJPAEntity;
import br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence.repository.UserJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRepositoryAdapter implements UserRepository {

    private final UserJPARepository userJPARepository;

    public UserRepositoryAdapter(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    @Override
    @Transactional
    public User create(User user) {
        return save(user);
    }

    private User save(final User user) {
        return userJPARepository.save(UserJPAEntity.of(user)).toUser();
    }

    @Override
    @Transactional
    public User update(User user) {
        return save(user);
    }

    @Override
    public Optional<User> findById(UserId anId) {
        return userJPARepository.findById(anId.value()).map(UserJPAEntity::toUser);
    }

    @Override
    public List<User> findAllByName(String name) {
        return userJPARepository.findAllByName(name).stream().map(UserJPAEntity::toUser).toList();
    }

    @Override
    public List<User> findAll() {
        return userJPARepository.findAll().stream().map(UserJPAEntity::toUser).toList();
    }

    @Override
    public void deleteById(UserId anId) {
        userJPARepository.deleteById(anId.value());
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userJPARepository.findByLogin(login).map(UserJPAEntity::toUser);
    }
}
