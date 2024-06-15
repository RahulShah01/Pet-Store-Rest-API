package pet.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.store.entity.Customer;

/*
 * Data Layer Interface CustomerDao created. This interface extends JpaRepository. This DAO
 * interface assists to manage the CRUD operation on the customer table. It is used by service
 * method to find an existing customer row.
 */

public interface CustomerDao extends JpaRepository<Customer, Long> {

}
