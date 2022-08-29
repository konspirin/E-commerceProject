package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
