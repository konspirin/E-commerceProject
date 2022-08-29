package application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entities.Attribute;

public interface AttributeRepository extends JpaRepository<Attribute, Integer> {

}
