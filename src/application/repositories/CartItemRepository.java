package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
