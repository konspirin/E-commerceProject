package application.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import application.datamembers.AttrName;
import application.entities.AttrValue;

public interface AttrValueRepository extends JpaRepository<AttrValue, Long> {

	List<AttrValue> findByAttributeAttrName(AttrName newArrival);

	List<AttrValue> findByValueAndValue(String string, String string2);

	List<AttrValue> findByValue(String string);

	List<AttrValue> findByValueIn(Set<String> of);

}
