package pet.store.service;

import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

  /*
   * Added PetStoreDao object petStoreDao as a private instance variable.
   * 
   * @Autowired annotation added so that the Spring can inject the DAO object into the variable.
   */
  @Autowired
  private PetStoreDao petStoreDao;


  // savePetStore method takes a PetStoreData object as a parameter and returns a PetStoreData
  // object.

  @Transactional(readOnly = false)
  public PetStoreData savePetStore(PetStoreData petStoreData) {
    Long petStoreId = petStoreData.getPetStoreId();
    PetStore petStore = findOrCreatePetStore(petStoreId);
    copyPetStoreFields(petStore, petStoreData);

    // returning new PetStoreData Object created from the return value of the save() method.
    return new PetStoreData(petStoreDao.save(petStore));

  }

  /*
   * findOrCreatePetStore takes petStoreId as a parameter and returns a new PetStore object if the
   * pet store ID is null. If not null, this method calls findPetStoreById method.
   */

  private PetStore findOrCreatePetStore(Long petStoreId) {
    PetStore petStore;

    if (Objects.isNull(petStoreId)) {
      petStore = new PetStore();
    } else {
      petStore = findPetStoreById(petStoreId);
    }
    return petStore;
  }

  /*
   * findPetStoreById method takes in petStoreId as a parameter. It returns PetStore object if a pet
   * store with matching IF exists in the database. If no matching pet store is found, it throws
   * NoSuchElementException.
   */
  private PetStore findPetStoreById(Long petStoreId) {
    return petStoreDao.findById(petStoreId).orElseThrow(
        () -> new NoSuchElementException("Pet Store with ID = " + petStoreId + " does not exist"));
  }

  /*
   * copyPetStoreFields method takes PetStore object and PetStoreData object as parameters. Matching
   * fields are copied from the PetStoreData object to the PetStore object
   */
  private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
    petStore.setPetStoreId(petStoreData.getPetStoreId());
    petStore.setPetStoreName(petStoreData.getPetStoreName());
    petStore.setPetStoreAddress(petStoreData.getPetStoreAddress());
    petStore.setPetStoreCity(petStoreData.getPetStoreCity());
    petStore.setPetStoreState(petStoreData.getPetStoreState());
    petStore.setPetStoreZip(petStoreData.getPetStoreZip());
    petStore.setPetStorePhone(petStoreData.getPetStorePhone());

  }
}
