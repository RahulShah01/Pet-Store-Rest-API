package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.PetStore;

// Data Layer Interface PetStoreDao created. This interface extends JpaRepository.

public interface PetStoreDao extends JpaRepository<PetStore, Long> {

}
