package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

/*
 * Created PetStoreController class.
 * 
 * @RestContoller annotation added to tell the Spring that this is a REST controller.
 * 
 * @RequestMapping annotation added to tell the Spring that the URI for every HTTP request that is
 * mapped to a method in this controller class must start with "/pet_store
 * 
 * @Slf4j annotation added to create SLF4J logger.
 * 
 * @Autowired added to PetStoreService as an instance variable.
 */
@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

  @Autowired
  private PetStoreService petStoreService;

  /*
   * Method to map HTTP POST request to "/pet_store" with 201 Created response. This method returns
   * a PetStoreData object and logs the request. savePetStore (from service class) is called that
   * will insert or modify the pet store data.
   */
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
    log.info("Creating Pet Store {}", petStoreData);
    return petStoreService.savePetStore(petStoreData);
  }

  /*
   * Method to update the existing pet store data with using the pet store Id. This method returns a
   * PetStoreData object and logs the request. savePetStore (from service class) is called that will
   * modify the pet store data to with new values.
   */

  @PutMapping("/{petStoreId}")
  public PetStoreData updatePetStore(@PathVariable Long petStoreId,
      @RequestBody PetStoreData petStoreData) {
    petStoreData.setPetStoreId(petStoreId);
    log.info("Updating Pet Store {}", petStoreData);
    return petStoreService.savePetStore(petStoreData);
  }

}
