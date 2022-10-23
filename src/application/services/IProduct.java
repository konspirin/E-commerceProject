package application.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import application.api.ReturnCode;
import application.datamembers.*;
import application.dto.*;
import application.exceptions.DatabaseEmptyException;


public interface IProduct {
	ReturnCode addAllProductsRandom(int number);
	ReturnCode addProduct(ProductDto product);
	//------------GETTERS------------------------
	ProductDto getProduct(long prodId);
	ProductDto getProductByArtikul(String artikul);
//	List<ProductBaseInfoDto> getAllProductsBaseInfo(int pageNumber, int pageSize) throws DatabaseEmptyException;
	List<ProductBaseInfoDto> getAllProductsBaseInfo(Pageable page) throws DatabaseEmptyException;
	ResponsePageProdBaseInfo getAllProductsBI(Pageable page)throws DatabaseEmptyException;
	List<ProductBaseInfoDto> getNewArrivalProducts();
	List<ProductBaseInfoDto> getProductsByGender(Gender gender);
	List<ProductBaseInfoDto> getProductsByCollection(Collection coll);
	List<ProductBaseInfoDto> getProductsByCategory(Category category);
	List<ProductBaseInfoDto> getProductsByGenderAndCategory(Gender gender, Category category);
	List<ProductBaseInfoDto> getProductsByGenderAndCollection(Gender gender, Collection coll);
	List<ProductBaseInfoDto> getProductsByCollectionAndCategory(Collection coll, Category category);
	List<ProductBaseInfoDto> getProductsWithDiscount();
	List<ProductBaseInfoDto> getProductsWithPriceBetween(double min, double max);
	List<ProductBaseInfoDto> getProductsByBrand(Brand brand);
	List<ProductBaseInfoDto> getProductsBySeason(Season season);
	List<ProductBaseInfoDto> getProductsByStyle(Style style);
	List<ProductBaseInfoDto> getOurFavoriteProducts();
	List<ProductBaseInfoDto> getNewCollectionProducts();
	List<ProductBaseInfoDto> getTrendProducts();
	List<ProductBaseInfoDto> getNewNameProducts();
	List<ProductBaseInfoDto> getLuxProducts();
	List<ProductBaseInfoDto> getExclusiveProducts();
	List<ProductBaseInfoDto> getProductsByAttributeAndValue(String attrName, String attrValue);
	ResponsePageProdBaseInfo getProductsByAttributeAndValueByPages(String attrName, String attrValue, Pageable page);
	List<ProductBaseInfoDto> getProductsByTwoAttributesAndValues(String attr1, String value1, String attr2, String value2);
	ResponsePageProdBaseInfo getProductsByTwoAttributesAndValuesByPages(String attr1, String value1,
			String attr2, String value2, Pageable page);
	
	//------------SETTERS------------------------
	ReturnCode updateProduct(long prodId, ProductDto product);
	ReturnCode updateProductPrice(long prodId, double newPrice);
	ReturnCode updateProductDiscount(long prodId, int newDiscount);

}
