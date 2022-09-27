package application.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.datamembers.AttrName;
import application.datamembers.Category;
import application.datamembers.Gender;
import application.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByArtikul(String artikul);

	List<Product> findByCategory(Category category);

	List<Product> findByAttrValuesValueAndAttrValuesValue(String string, String string2);

	List<Product> findByAttrValuesValueAndCategory(String string, Category category);

	List<Product> findByDiscountNotNull();




}
