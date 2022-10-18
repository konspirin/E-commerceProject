package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.api.ReturnCode;
import application.datamembers.Brand;
import application.datamembers.Category;
import application.datamembers.Collection;
import application.datamembers.Gender;
import application.datamembers.Season;
import application.datamembers.Style;
import application.dto.ProductBaseInfoDto;
import application.dto.ProductDto;
import application.exceptions.DatabaseEmptyException;
import application.exceptions.ProductNotFoundException;
import application.services.IProduct;

@RestController
@CrossOrigin
public class ProductController {

	@Autowired
	IProduct prodService;
	
	@PostMapping(value="/number_products_add")
	ReturnCode addAllProductsRandom(@RequestParam int number) {
		return prodService.addAllProductsRandom(number);
	}
	
	@PostMapping(value = "/product_add")
	ReturnCode addProduct(@RequestBody ProductDto product) {
		return prodService.addProduct(product);
	}

//	@GetMapping(value = "/product_by_id_get")
//	ResponseEntity<ProductDto> getProduct(@RequestParam long prodId) {
//		try {
//			return new ResponseEntity<ProductDto>(prodService.getProduct(prodId), HttpStatus.OK) ;
//		} catch (Exception e) {
//			throw new ProductNotFoundException("Here isn't any product with this ID"+prodId);
//		}
//	}
	@GetMapping(value = "/product_by_id_get")
	ProductDto getProduct(@RequestParam long prodId) {	
			return prodService.getProduct(prodId) ;
	}
	@GetMapping(value = "/product_by_artikul_get")
	ProductDto getProductByArtikul(@RequestParam String artikul) {
		return prodService.getProductByArtikul(artikul);
	}
	@GetMapping(value = "/all_products_get")
	List<ProductBaseInfoDto> getAllProductsBaseInfo(@RequestParam int pageNumber, @RequestParam int pageSize) throws DatabaseEmptyException{
		return prodService.getAllProductsBaseInfo(pageNumber, pageSize);
	}
	@GetMapping(value = "/new_arrivals_get")
	List<ProductBaseInfoDto> getNewArrivalProducts(){
		return prodService.getNewArrivalProducts();
	}
	@GetMapping(value = "/discount_products_get")
	List<ProductBaseInfoDto> getProductsWithDiscount(){
		return prodService.getProductsWithDiscount();
	}
	@GetMapping(value = "/product_by_gender_get")
	List<ProductBaseInfoDto> getProductsByGender(@RequestParam Gender gender){
		return prodService.getProductsByGender(gender);
	}
	@GetMapping(value = "/product_by_collection_get")
	List<ProductBaseInfoDto> getProductsByCollection(@RequestParam Collection coll){
		return prodService.getProductsByCollection(coll);
	}
	@GetMapping(value = "/product_by_category_get")
	List<ProductBaseInfoDto> getProductsByCategory(@RequestParam Category category){
		return prodService.getProductsByCategory(category);
	}
	@GetMapping(value = "/product_by_gender_and_category_get")
	List<ProductBaseInfoDto> getProductsByGenderAndCategory(@RequestParam Gender gender, @RequestParam Category category){
		return prodService.getProductsByGenderAndCategory(gender, category);
	}
	@GetMapping(value = "/product_by_gender_and_collection_get")
	List<ProductBaseInfoDto> getProductsByGenderAndCollection(@RequestParam Gender gender, @RequestParam Collection coll){
		return prodService.getProductsByGenderAndCollection(gender, coll);
	}
	@GetMapping(value = "/product_by_collection_and_category_get")
	List<ProductBaseInfoDto> getProductsByCollectionAndCategory(@RequestParam Collection coll, @RequestParam Category category){
		return prodService.getProductsByCollectionAndCategory(coll, category);
	}
	
	@PutMapping(value = "/product_update")
	ReturnCode updateProduct(@RequestParam long prodId, @RequestBody ProductDto product) {
		return prodService.updateProduct(prodId, product);
	}
	@PatchMapping(value = "/product_price_update")
	ReturnCode updateProductPrice(@RequestParam long prodId, @RequestParam double newPrice) {
		return prodService.updateProductPrice(prodId, newPrice);
	}
	
	@PatchMapping(value = "/product_discount_update")
	ReturnCode updateProductDiscount(@RequestParam long prodId, @RequestParam int newDiscount) {
		return prodService.updateProductDiscount(prodId, newDiscount);
	}
	//----------------------------------------------------------------

	
	@GetMapping(value="/product_price_between_get")
	List<ProductBaseInfoDto> getProductsWithPriceBetween(@RequestParam double min, @RequestParam double max){
		return prodService.getProductsWithPriceBetween(min, max);
	}
	
	@GetMapping(value = "/product_by_brand_get")
	List<ProductBaseInfoDto> getProductsByBrand(Brand brand){
		return prodService.getProductsByBrand(brand);
	}
	
	@GetMapping(value = "/product_by_season_get")
	List<ProductBaseInfoDto> getProductsBySeason(Season season){
		return prodService.getProductsBySeason(season);
	}
	
	@GetMapping(value = "/product_by_style_get")
	List<ProductBaseInfoDto> getProductsByStyle(Style style){
		return prodService.getProductsByStyle(style);
	}
	
	@GetMapping(value = "/our_favorite_products_get")
	List<ProductBaseInfoDto> getOurFavoriteProducts(){
		return prodService.getOurFavoriteProducts();
	}
	
	@GetMapping(value = "/new_collection_products_get")
	List<ProductBaseInfoDto> getNewCollectionProducts(){
		return prodService.getNewCollectionProducts();	
	}
	
	@GetMapping(value = "/trend_products_get")
	List<ProductBaseInfoDto> getTrendProducts(){
		return prodService.getTrendProducts();		
	}
	
	@GetMapping(value = "/new_name_products_get")
	List<ProductBaseInfoDto> getNewNameProducts(){
		return prodService.getNewNameProducts();
	}
	
	@GetMapping(value = "/lux_products_get")
	List<ProductBaseInfoDto> getLuxProducts(){
		return prodService.getLuxProducts();
	}
	
	@GetMapping(value = "/exclusive_products_get")
	List<ProductBaseInfoDto> getExclusiveProducts(){
		return prodService.getExclusiveProducts();
	}
	
	
}
