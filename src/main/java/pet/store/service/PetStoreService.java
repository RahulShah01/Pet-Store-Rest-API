package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
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

  @Autowired
  private EmployeeDao employeeDao;

  @Autowired
  private CustomerDao customerDao;


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

  /*
   * Methods associated with adding PetStore Employee.
   * 
   * saveEmployee() method: takes a petstoreId and petStoreEmployee as parameters. returns
   * PetStoreEmployee.
   * 
   * findEmployeeById() method: takes petStoreId and employeeId as parameters. Using the employeeDao
   * method findById(), Employee object is returned. If the employee isn't found, new
   * NoSuchElementException() is thrown.
   * 
   * findOrCreateEmployee(): takes employeeId and petStoreId as parameters. returns Employee object.
   * If employee Id is null, new Employee object is returned. if employee is not null,
   * findEmployeeById() method is called.
   * 
   */

  @Transactional(readOnly = false)
  public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {

    PetStore petStore = findPetStoreById(petStoreId);
    Employee employee = findOrCreateEmployee(petStoreEmployee.getEmployeeId(), petStoreId);
    copyEmployeeFields(employee, petStoreEmployee);
    employee.setPetStore(petStore);
    petStore.getEmployees().add(employee);
    Employee dbEmployee = employeeDao.save(employee);

    return new PetStoreEmployee(dbEmployee);

  }

  private Employee findEmployeeById(Long petStoreId, Long employeeId) {
    Employee employee = employeeDao.findById(employeeId).orElseThrow(
        () -> new NoSuchElementException("Employee with ID=" + employeeId + " does not exist"));

    if (employee.getPetStore().getPetStoreId() != petStoreId) {
      throw new IllegalArgumentException("Employee with ID= " + employeeId
          + " does not belong to pet store with ID=" + petStoreId);
    }

    return employee;
  }

  private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
    Employee employee;

    if (Objects.isNull(employeeId)) {
      employee = new Employee();
    } else {
      employee = findEmployeeById(petStoreId, employeeId);
    }
    return employee;
  }

  /*
   * copyEmployeeFields method takes Employee object and PetStoreEmployee object as parameters.
   * Matching fields are copied from the PetStoreEmployee object to the Employee object.
   */

  private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
    employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
    employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
    employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
    employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
  }

  /*
   * Methods associated with adding PetStore Customer.
   * 
   * saveCustomer() method: takes a petstoreId and petStoreCustomer as parameters. returns
   * PetStoreCustomer.
   * 
   * findCustomerById() method: takes petStoreId and customerId as parameters.Using the customerDao
   * method findById(), Customer object is returned. If the customer isn't found, new
   * NoSuchElementException() is thrown.
   * 
   * Customer object has a list of PetStore Object. We loop through the list of PetStore objects
   * looking for the petStore with given pet store Id. If customer is not found in the particular
   * petStore, IllegalArgumentException is thrown.
   * 
   * findOrCreateCustomer(): takes customerId and petStoreId as parameters. returns Customer object.
   * If customer Id is null, new customer object is returned. if customer is not null,
   * findCustomerById() method is called.
   * 
   */

  @Transactional(readOnly = false)
  public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {

    PetStore petStore = findPetStoreById(petStoreId);
    Customer customer = findOrCreateCustomer(petStoreCustomer.getCustomerId(), petStoreId);
    copyCustomerFields(customer, petStoreCustomer);
    customer.getPetStore().add(petStore);
    petStore.getCustomers().add(customer);
    Customer dbCustomer = customerDao.save(customer);

    return new PetStoreCustomer(dbCustomer);

  }

  private Customer findCustomerById(Long petStoreId, Long customerId) {
    Customer customer = customerDao.findById(customerId).orElseThrow(
        () -> new NoSuchElementException("Customer with ID=" + customerId + " does not exist"));

    boolean found = false;

    for (PetStore petStore : customer.getPetStore()) {
      if (petStore.getPetStoreId() == petStoreId) {
        found = true;
        break;
      }
    }
    if (!found) {
      throw new IllegalArgumentException(
          "Customer with ID= " + customerId + " does not shop at pet store with ID=" + petStoreId);
    }
    return customer;

  }

  private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
    Customer customer;

    if (Objects.isNull(customerId)) {
      customer = new Customer();
    } else {
      customer = findCustomerById(petStoreId, customerId);
    }
    return customer;
  }

  /*
   * copyCustomerFields method takes Customer object and PetStoreCustomer object as parameters.
   * Matching fields are copied from the PetStoreCustomer object to the Customer object.
   */

  private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
    customer.setCustomerId(petStoreCustomer.getCustomerId());
    customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
    customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
    customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());

  }

  /*
   * Method to list all pet stores. This methods takes no parameters. Call is made to findAll()
   * method in the petStoreDao. List of PetStore objects is converted to PetStoreData objects. All
   * the customer and employee objects in each PetStoreData object is removed. Summary list of pet
   * stores is returned.
   * 
   */
  @Transactional(readOnly = true)
  public List<PetStoreData> retrieveAllPetStore() {
    List<PetStore> petStores = petStoreDao.findAll();

    List<PetStoreData> result = new LinkedList<>();

    for (PetStore petStore : petStores) {
      PetStoreData psd = new PetStoreData(petStore);

      psd.getCustomers().clear();
      psd.getEmployees().clear();

      result.add(psd);
    }
    return result;
  }

  /*
   * Method to list petStore associated with the petStore Id. Takes in petStoreId as a parameter,
   * findById() method is called, results is converted to PetStoreData object and returned.
   * 
   */

  @Transactional(readOnly = true)
  public PetStoreData retrievePetStoreByPetStoreId(Long petStoreId) {
    PetStore petStore = findPetStoreById(petStoreId);
    return new PetStoreData(petStore);
  }

  /*
   * Method to delete petStore associated with the petStore Id. This method take petStoreId as
   * parameter, calls findPetStoreById() method to retrieve the PetStore entity. And calls the
   * delete() method in the PetStoreDao interface, passing petStore entity.
   */
  
  @Transactional(readOnly = false)
  public void deletePetStoreById(Long petStoreId) {
    PetStore petStore = findPetStoreById(petStoreId);
    petStoreDao.delete(petStore);
  }
  
}
