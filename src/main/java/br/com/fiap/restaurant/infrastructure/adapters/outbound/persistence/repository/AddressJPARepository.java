package br.com.fiap.restaurant.infrastructure.adapters.outbound.persistence.repository;

import br.com.fiap.restaurant.infrastructure.adapters.outbound.persistence.entity.AddressJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressJPARepository extends JpaRepository<AddressJPAEntity, String> {
}
