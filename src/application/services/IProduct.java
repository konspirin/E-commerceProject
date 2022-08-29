package application.services;

import java.util.List;

import application.api.ReturnCode;
import application.datamembers.*;
import application.dto.*;


public interface IProduct {
	ReturnCode addAllProductsRandom(int number);
	ReturnCode addProduct(ProductDto product);
	//------------GETTERS------------------------
	ProductDto getProduct(long prodId);
	ProductDto getProductByArtikul(String artikul);
	List<ProductBaseInfoDto> getAllProductsBaseInfo();
	
	List<ProductBaseInfoDto> getNewArrivalProducts();
	List<ProductBaseInfoDto> getProductsByGender(Gender gender);
	List<ProductBaseInfoDto> getProductsByCollection(Collection coll);
	List<ProductBaseInfoDto> getProductsByCategory(Category category);
	List<ProductBaseInfoDto> getProductsByGenderAndCategory(Gender gender, Category category);
	List<ProductBaseInfoDto> getProductsByGenderAndCollection(Gender gender, Collection coll);
	List<ProductBaseInfoDto> getProductsByCollectionAndCategory(Collection coll, Category category);
	
	
	//------------SETTERS------------------------
	ReturnCode updateProduct(ProductDto product);
	ReturnCode updateProductPrice(float newPrice);

}
