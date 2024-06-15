package pet.store.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
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

  /*
   * Method to add pet store employee. This method allows an employee to be added to a pet store.
   * using HTTP POST request to "/pet_store/{pet_store}/employee" with 201 created response. This
   * methods calls the saveEmployee() method in the service class and returns the result of that
   * method call.
   */

  @PostMapping("/{petStoreId}/employee")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PetStoreEmployee addPetStoreEmployee(@PathVariable Long petStoreId,
      @RequestBody PetStoreEmployee petStoreEmployee) {
    log.info("Adding pet store employee for pet store ID: {}", petStoreEmployee, petStoreId);
    return petStoreService.saveEmployee(petStoreId, petStoreEmployee);
  }

  /*
   * Method to add pet store customer. This method allows customer to be added to a pet store. using
   * HTTP POST request to "/pet_store/{pet_store}/customer" with 201 created response. This methods
   * calls the saveCustomer() method in the service class and returns the result of that method
   * call.
   */

  @PostMapping("/{petStoreId}/customer")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PetStoreCustomer addPetStoreCustomer(@PathVariable Long petStoreId,
      @RequestBody PetStoreCustomer petStoreCustomer) {
    log.info("Adding pet store customer for pet store ID: {}", petStoreCustomer, petStoreId);
    return petStoreService.saveCustomer(petStoreId, petStoreCustomer);
  }

  /*
   * Method to list all pet stores. This methods returns a List of pet stores.
   * 
   * @GetMapping annotation is added, this annotation does not take a value. Calls
   * retrieveAllPetStore() method is the service class.
   * 
   */

  @GetMapping
  public List<PetStoreData> retrieveAllPetStore() {
    log.info("Retrieve all pet stores");
    return petStoreService.retrieveAllPetStore();
  }

  /*
   * Method to retrieve pet store by its ID. This method retrieves single pet store associated with
   * the given pet store ID.
   * 
   * @GetMapping annotation is added, this annotation takes in the pet store ID that is passed in to
   * the method as a parameter. Calls the retrievePetStoreByPetStoreId() method in the service
   * class.
   * 
   */

  @GetMapping("/{petStoreId}")
  public PetStoreData retrievePetStoreByPetStoreId(@PathVariable Long petStoreId) {
    log.info("Retriving pet store with ID={}", petStoreId);
    return petStoreService.retrievePetStoreByPetStoreId(petStoreId);
  }

  /*
   * Method to delete petStore by petStore Id. Takes petStoreId as parameter.
   * 
   * @DeleteMapping annotation is used to delete petStore associated with the specific petStoreId.
   * deletePetStoreById() method is called from the service class which takes in petStoreId as
   * parameter and returns Map<String, String> where the key is "message" and the value is the
   * deletion successful message.
   * 
   */
  
  @DeleteMapping("/{petStoreId}")
  public Map<String, String> deletePetStorerById(@PathVariable Long petStoreId) {
    log.info("Deleting Pet Store with ID={}", petStoreId);
    petStoreService.deletePetStoreById(petStoreId);
    return Map.of("message", "Deletion of Pet Store with ID=" + petStoreId + " was successful.");
  }
  
}
