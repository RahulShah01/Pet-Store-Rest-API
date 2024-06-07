package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

/*
 * In this section DTO class that Jackson will use to transform the object to and from JSON is
 * created. DTO class PetStoreData is created. Copied the fields from the PetStore entity class and
 * added @Data and @NoArgsConstructor from the lombok package.
 */

@Data
@NoArgsConstructor
public class PetStoreData {

  private Long petStoreId;
  private String petStoreName;
  private String petStoreAddress;
  private String petStoreCity;
  private String petStoreState;
  private String petStoreZip;
  private String petStorePhone;
  private Set<PetStoreCustomer> customers = new HashSet<>(); // Data type of the customers field
                                                             // changed to PetStoreCustomer.

  private Set<PetStoreEmployee> employees = new HashSet<>(); // Data type of the employees field
                                                             // changed to PetStoreEmployee.

  // Constructor: takes PetStore as a parameter.
  // Setting all matching fields in the PetStoreData class to the data in the PetStore class.
  public PetStoreData(PetStore petStore) {
    petStoreId = petStore.getPetStoreId();
    petStoreName = petStore.getPetStoreName();
    petStoreAddress = petStore.getPetStoreAddress();
    petStoreCity = petStore.getPetStoreCity();
    petStoreState = petStore.getPetStoreState();
    petStoreZip = petStore.getPetStoreZip();
    petStorePhone = petStore.getPetStorePhone();

    /*
     * Using loops to set customers and employees fields to the respective PetStoreCustomer and
     * PetStoreEmployee
     */

    for (Customer customer : petStore.getCustomers()) {
      customers.add(new PetStoreCustomer(customer));
    }

    for (Employee employee : petStore.getEmployees()) {
      employees.add(new PetStoreEmployee(employee));
    }
  }
}
