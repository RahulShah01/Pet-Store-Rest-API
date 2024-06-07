package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;

/*
 * In this section DTO class PetStoreCustomer is created. Copied the fields from the Customer entity
 * class and added @Data and @NoArgsConstructor from the lombok package.
 */

@Data
@NoArgsConstructor
public class PetStoreCustomer {

  private Long customerId;

  private String customerFirstName;
  private String customerLastName;
  private String customerEmail;

  // Constructor: takes a Customer object.
  // Setting all matching fields in the PetStoreCustomer class to the data in the Customer class.
  public PetStoreCustomer(Customer customer) {

    customerId = customer.getCustomerId();
    customerFirstName = customer.getCustomerFirstName();
    customerLastName = customer.getCustomerLastName();
    customerEmail = customer.getCustomerEmail();
  }

}
