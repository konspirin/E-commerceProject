package application.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import application.api.ReturnCode;
import application.datamembers.AttrName;
import application.datamembers.Brand;
import application.datamembers.Category;
import application.datamembers.Collection;
import application.datamembers.Gender;
import application.datamembers.Season;
import application.datamembers.Style;
import application.dto.ProductBaseInfoDto;
import application.dto.ProductDto;
import application.dto.ResponsePageProdBaseInfo;
import application.entities.AttrValue;
import application.entities.Attribute;
import application.entities.Product;
import application.exceptions.DatabaseEmptyException;
import application.exceptions.ProductNotFoundException;
import application.repositories.AttrValueRepository;
import application.repositories.AttributeRepository;
import application.repositories.ProductRepository;

@Service
public class ProductService implements IProduct{
	
	@Autowired
	ProductRepository productRepo;
	@Autowired
	AttrValueRepository attrValueRepo;
	@Autowired
	AttributeRepository attributeRepo;
	
	//*************MAPPERS***********************
	
	private Product productDtoToProductMapper(ProductDto productDto) {
		Product product = new Product(productDto.getName(), productDto.getArtikul(),
				Category.valueOf(productDto.getCategory()),
				productDto.getPrice(),
				productDto.getDiscount(),
				productDto.getImgBox(),
				productDto.getRating());
				
		return product;
	}
	
	private ProductDto productToProductDtoMapper(Product prod) {
		ProductDto prodDto = new ProductDto(prod.getId(), prod.getName(), prod.getArtikul(), prod.getCategory().toString(),
				prod.getPrice(), prod.getDiscount(),
				prod.getImgBox(), prod.getRating(), setAttrValueToMap(prod.getAttrValues()));
		return prodDto;
	}
	
	private Map<String, String> setAttrValueToMap(Set<AttrValue> attrValues) {
		Map<String, String> res = new HashMap<>();
		attrValues.forEach(av -> res.put(av.getAttribute().getAttrName().toString(), av.getValue()));
		return res;
	}

	private ProductBaseInfoDto productToProductBaseInfoDtoMapper(Product prod) {
		ProductBaseInfoDto PBIDto = new ProductBaseInfoDto(
				prod.getId(), prod.getName(), prod.getArtikul(), prod.getPrice(),
				prod.getDiscount(), prod.getImgBox().getThumbImg(), prod.getRating());
		return PBIDto;
	}
	private AttrValue attrValueMapper(Entry<String, String> e, long id) {
		Attribute attr = new Attribute(AttrName.valueOf(e.getKey()), "text", true);
		attributeRepo.save(attr);
		AttrValue attrValue = new AttrValue(productRepo.findById(id).orElse(null),
				attr,
				(String)e.getValue());
		return attrValue;
	}
	
	private List<ProductBaseInfoDto> ListProductToListProductDtoMapper(List<Product> products) {
		return products.stream().map(p -> productToProductBaseInfoDtoMapper(p))
				.collect(Collectors.toList());
	}
//****************RANDOM FILLING DB PRODUCTS******************************
	@Transactional
	public ReturnCode addAllProductsRandom(int number) {
		for(int i=0; i<number; i++) 
			addProduct(ProductDto.randomProductDto());
		return ReturnCode.OK;
	}
//****************SERVICE METHODS*****************************************
	@Override
	@Transactional
	public ReturnCode addProduct(ProductDto productDto) {
		if(productRepo.existsById(productDto.getId()))
			return ReturnCode.PRODUCT_ALREADY_EXISTS;
	
		//***********first we create Product instance **********************
		Product prod = productDtoToProductMapper(productDto);
		productRepo.save(prod);
		
		//**********Then we create number of AttrValue objects for this Product instance**********
		productDto.getAttrValues().entrySet()
		.forEach(e ->{attrValueRepo.save(attrValueMapper(e, prod.getId()));} );
		
		return ReturnCode.OK;
	}



	@Override
	public ProductDto getProduct(long prodId) {
		if(!productRepo.existsById(prodId))
		throw new ProductNotFoundException("There isn't any product with ID "+prodId);
		Product prod = productRepo.findById(prodId).orElse(null);
		return productToProductDtoMapper(prod);
	}

	
//	@Override
//	public List<ProductBaseInfoDto> getAllProductsBaseInfo() throws DatabaseEmptyException {
//		List<Product> listProd = productRepo.findAll();
//		if(listProd.isEmpty())
//			throw new DatabaseEmptyException("Sorry. Database is empty");
//		List<ProductBaseInfoDto> res = new ArrayList<>();
//		listProd.forEach(p -> res.add(productToProductBaseInfoDtoMapper(p)));
//		return res;	
//	}

	@Override
	public List<ProductBaseInfoDto> getNewArrivalProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.NEW_ARRIVAL);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");
				
		return products;		 
	}

	private List<ProductBaseInfoDto> getProductListByAttrValue(List<AttrValue> list, String param) {
		return list.stream().filter(attr -> attr.getValue().equals(param))
			.map(a -> a.getProduct())
			.map(p -> productToProductBaseInfoDtoMapper(p))
			.collect(Collectors.toList());
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByGender(Gender gender) {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.GENDER);
		return getProductListByAttrValue(list, gender.toString());
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByCollection(Collection coll) {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.COLLECTION);
		return getProductListByAttrValue(list, coll.toString());
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByCategory(Category category) {
		List<Product> products = productRepo.findByCategory(category);
		if(products.isEmpty())
			return new ArrayList<ProductBaseInfoDto>();
		return ListProductToListProductDtoMapper(products);
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByGenderAndCategory(Gender gender, Category category) {
		List<Product> products = productRepo.findByAttrValuesValueAndCategory(gender.toString(),category);
		return ListProductToListProductDtoMapper(products);
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByGenderAndCollection(Gender gender, Collection coll) {
		List <AttrValue> list = attrValueRepo.findByValueIn(Set.of(gender.toString(),coll.toString()));
		if(list.isEmpty())
			return null;
		List<Long> prodIds = new ArrayList<>();
		Map<Long, Long> map = 
				list.stream().collect(Collectors.groupingBy(attr -> attr.getProduct().getId(), Collectors.counting()));
		map.entrySet().forEach(e -> {
			if(e.getValue().equals(2L))
				prodIds.add(e.getKey());
		});
		List<Product> products = new ArrayList<>();
		prodIds.forEach(id -> products.add(productRepo.findById(id).orElse(new Product())));
		
		return ListProductToListProductDtoMapper(products);
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByCollectionAndCategory(Collection coll, Category category) {
		List<Product> products = productRepo.findByAttrValuesValueAndCategory(coll.toString(),category);
		return ListProductToListProductDtoMapper(products);
	}

	@Override
	@Transactional
	public ReturnCode updateProduct(long prodId, ProductDto product) {	
		Product prod = getProductEntitybyId(prodId);
		Set <AttrValue> attrValues = prod.getAttrValues();
		attrValues.forEach(attrV -> attrValueRepo.delete(attrV));
		prod.setName(product.getName());
		prod.setArtikul(product.getArtikul());
		prod.setCategory(Category.valueOf(product.getCategory()));
		prod.setPrice(product.getPrice()); 
		prod.setDiscount(product.getDiscount());
		prod.setImgBox(product.getImgBox());
		prod.setRating(product.getRating());
		productRepo.save(prod);
		product.getAttrValues().entrySet()
		.forEach(e ->{attrValueRepo.save(attrValueMapper(e, prodId));} );
		
		return ReturnCode.OK;
	}

	@Override
	@Transactional
	public ReturnCode updateProductPrice(long prodId, double newPrice) {
			Product prod = getProductEntitybyId(prodId);
			prod.setPrice(newPrice);
			productRepo.save(prod);
		return ReturnCode.OK;
	}

	@Override
	public ProductDto getProductByArtikul(String artikul) {
		Product prod = productRepo.findByArtikul(artikul);
		return productToProductDtoMapper(prod);
	}

	@Override
	public List<ProductBaseInfoDto> getProductsWithDiscount() {
		List<Product> products = productRepo.findByDiscountNotNull();
		return products.stream().filter(prod -> prod.getDiscount()>0)
				.map(p -> productToProductBaseInfoDtoMapper(p)).collect(Collectors.toList());
	}

	@Override
	public ReturnCode updateProductDiscount(long prodId, int newDiscount) {
		Product prod = getProductEntitybyId(prodId);
		prod.setDiscount(newDiscount);
		productRepo.save(prod);
		return ReturnCode.OK;
	}

	private Product getProductEntitybyId(long prodId) {
		if(!productRepo.existsById(prodId))
			throw new ProductNotFoundException("There isn't any product with ID "+prodId);
		return productRepo.findById(prodId).orElse(null);
	}
//-----------------------------------------------------------------------------------------------
	@Override
	public List<ProductBaseInfoDto> getProductsWithPriceBetween(double min, double max) {
		List<Product> products = productRepo.findByPriceBetween(min, max);
		return ListProductToListProductDtoMapper(products);
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByBrand(Brand brand) {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.BRAND);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, brand.toString());
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getProductsBySeason(Season season) {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.SEASON);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, season.toString());
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByStyle(Style style) {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.STYLE);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, style.toString());
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getOurFavoriteProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.OUR_FAVORITE);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");			
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getNewCollectionProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.NEW_COLLECTION);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");				
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getTrendProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.TREND);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");				
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getNewNameProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.NEW_NAME);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");				
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getLuxProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.LUX);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");				
		return products;
	}

	@Override
	public List<ProductBaseInfoDto> getExclusiveProducts() {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.EXCLUSIVE);
		List<ProductBaseInfoDto> products = getProductListByAttrValue(list, "yes");				
		return products;
	}

//	@Override
//	public List<ProductBaseInfoDto> getAllProductsBaseInfo(int pageNumber, int pageSize) throws DatabaseEmptyException {
//		Pageable page = PageRequest.of(pageNumber, pageSize);
//		Page<Product> listProd = productRepo.findAll(page);
//		if(listProd.isEmpty())
//			throw new DatabaseEmptyException("Sorry. Database is empty");
//		List<ProductBaseInfoDto> res = new ArrayList<>();
//		listProd.forEach(p -> res.add(productToProductBaseInfoDtoMapper(p)));
//		return res;
//	}
	@Override
	public List<ProductBaseInfoDto> getAllProductsBaseInfo(Pageable page) throws DatabaseEmptyException {
//		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<Product> listProd = productRepo.findAll(page);
		if(listProd.isEmpty())
			throw new DatabaseEmptyException("Sorry. Database is empty");
		List<ProductBaseInfoDto> res = new ArrayList<>();
		listProd.forEach(p -> res.add(productToProductBaseInfoDtoMapper(p)));
		return res;
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByAttributeAndValue(String attrName, String attrValue) {
		List <AttrValue> list = attrValueRepo.findByAttributeAttrName(AttrName.valueOf(attrName));
		return getProductListByAttrValue(list, attrValue);
	}

	@Override
	public ResponsePageProdBaseInfo getAllProductsBI(Pageable page) throws DatabaseEmptyException {
		Page<Product> listProd = productRepo.findAll(page);
		if(listProd.isEmpty())
			throw new DatabaseEmptyException("Sorry. Database is empty");
		List<ProductBaseInfoDto> res = new ArrayList<>();
		listProd.forEach(p -> res.add(productToProductBaseInfoDtoMapper(p)));
		int totalPages = listProd.getTotalPages();
		int pageNum = listProd.getNumber();
		int totatItems = listProd.getSize();
		return new ResponsePageProdBaseInfo(totatItems, pageNum, totalPages, res);
	}

	

}
