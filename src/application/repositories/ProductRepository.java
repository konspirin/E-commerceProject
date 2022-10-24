package application.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.datamembers.AttrName;
import application.datamembers.Category;
import application.datamembers.Gender;
import application.dto.ProductBaseInfoDto;
import application.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Page<Product> findAll(Pageable pageable);

	Product findByArtikul(String artikul);

	List<Product> findByCategory(Category category);

	List<Product> findByAttrValuesValueAndAttrValuesValue(String string, String string2);

	List<Product> findByAttrValuesValueAndCategory(String string, Category category);

	List<Product> findByDiscountNotNull();

	List<Product> findByPriceBetween(double min, double max);

	@Query(value = "select p.*, av.value from (select pr.* from products pr join attribute_values av1 on pr.id = av1.product_id join attribute a1 on av1.attribute_id = a1.id where a1.attr_name =:attr1 and av1.value = :value1) p join attribute_values av on p.id = av.product_id join attribute a on av.attribute_id = a.id where a.attr_name = :attr2 and av.value = :value2 order by price asc",
			nativeQuery = true)
	List<Product> getProductsByTwoAttributesAndValues(
			@Param("attr1") String attr1,@Param("value1")String value1,	@Param("attr2") String attr2, @Param("value2") String value2);

	@Query(value = "select p.*, av.value from (select pr.* from products pr join attribute_values av1 on pr.id = av1.product_id join attribute a1 on av1.attribute_id = a1.id where a1.attr_name =:attr1 and av1.value = :value1) p join attribute_values av on p.id = av.product_id join attribute a on av.attribute_id = a.id where a.attr_name = :attr2 and av.value = :value2 order by price asc",
			nativeQuery = true)
	Page<Product> getProductsByTwoAttributesAndValuesByPages(
			@Param("attr1") String attr1,@Param("value1")String value1,	@Param("attr2") String attr2, @Param("value2") String value2, Pageable page);

	@Query(value = "select p.* from products p join attribute_values av on p.id = av.product_id join attribute a on av.attribute_id = a.id where a.attr_name =:attr and av.value = :value order by price asc",
			nativeQuery = true)
	List<Product> findByAttributeAndValue(@Param("attr")String attr, @Param("value")String value);


}
