package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Employee;

/*
 * Data Layer Interface EmployeeDao created. This interface extends JpaRepository. This DAO
 * interface assists to manage the CRUD operation on the employee table. It is used by service
 * method to find an existing Employee row.
 */

public interface EmployeeDao extends JpaRepository<Employee, Long> {

}
