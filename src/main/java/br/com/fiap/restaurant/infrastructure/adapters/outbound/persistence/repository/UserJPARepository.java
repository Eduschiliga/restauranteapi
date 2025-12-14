package br.com.fiap.restaurant.infrastructure.adapters.outbound.persistence.repository;

import br.com.fiap.restaurant.infrastructure.adapters.outbound.persistence.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, String> {
    Optional<UserJPAEntity> findByLogin(String login);

    @Query("FROM UserJPAEntity WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY name ASC")
    List<UserJPAEntity> findAllByName(String name);
}
