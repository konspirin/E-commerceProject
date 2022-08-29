package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
