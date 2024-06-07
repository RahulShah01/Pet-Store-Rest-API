package pet.store.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;

/*
 * In this section DTO class PetStoreEmployee is created. Copied the fields from the Employee entity
 * class and added @Data and @NoArgsConstructor from the lombok package.
 */

@Data
@NoArgsConstructor
public class PetStoreEmployee {

  private Long employeeId;

  private String employeeFirstName;
  private String employeeLastName;
  private String employeePhone;
  private String employeeJobTitle;

  // Constructor: takes an employee object.
  // Setting all matching fields in the PetStoreEmployee class to the data in the Employee class.
  public PetStoreEmployee(Employee employee) {

    employeeId = employee.getEmployeeId();
    employeeFirstName = employee.getEmployeeFirstName();
    employeeLastName = employee.getEmployeeLastName();
    employeePhone = employee.getEmployeePhone();
    employeeJobTitle = employee.getEmployeeJobTitle();

  }
}
