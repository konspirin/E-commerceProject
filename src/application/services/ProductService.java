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
import org.springframework.stereotype.Service;

import application.api.ReturnCode;
import application.datamembers.AttrName;
import application.datamembers.Category;
import application.datamembers.Collection;
import application.datamembers.Gender;
import application.dto.ProductBaseInfoDto;
import application.dto.ProductDto;
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
//				Collection.valueOf(productDto.getCollection()),
//				Gender.valueOf(productDto.getGender()),
				productDto.getPrice(),
				productDto.getOld_price(),
				productDto.getImgBox(),
				productDto.getRating());
				
		
//		List<String> temp_img_urls = new ArrayList<>();
//		temp_img_urls.add(productDto.getImg_url());
//		product.setImg_urls(temp_img_urls);
			
		return product;
	}
	
	private ProductDto productToProductDtoMapper(Product prod) {
		ProductDto prodDto = new ProductDto(prod.getId(), prod.getName(), prod.getArtikul(), prod.getCategory().toString(),
//				prod.getCollection().toString(), prod.getGender().toString(),
				prod.getPrice(), prod.getOld_price(),
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
				prod.getOld_price(), prod.getImgBox().getThumbImg(), prod.getRating());
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

	
	@Override
	public List<ProductBaseInfoDto> getAllProductsBaseInfo() throws DatabaseEmptyException {
		List<Product> listProd = productRepo.findAll();
		List<ProductBaseInfoDto> res = new ArrayList<>();
		if(listProd.isEmpty())
			throw new DatabaseEmptyException("Sorry. Database is empty");
		listProd.forEach(p -> res.add(productToProductBaseInfoDtoMapper(p)));
		return res;
		

	}

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
		List<Product> product = productRepo.findByCategory(category);
		if(product.isEmpty())
			return new ArrayList<ProductBaseInfoDto>();
		
		return product.stream().map(p -> productToProductBaseInfoDtoMapper(p))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByGenderAndCategory(Gender gender, Category category) {
		List<Product> product = productRepo.findByAttrValuesValueAndCategory(gender.toString(),category);
		return product.stream().map(p -> productToProductBaseInfoDtoMapper(p))
				.collect(Collectors.toList());
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
		List<Product> prod = new ArrayList<>();
		prodIds.forEach(id -> prod.add(productRepo.findById(id).orElse(new Product())));
		
		return prod.stream().map(p -> productToProductBaseInfoDtoMapper(p)).collect(Collectors.toList());
	}

	@Override
	public List<ProductBaseInfoDto> getProductsByCollectionAndCategory(Collection coll, Category category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCode updateProduct(ProductDto product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCode updateProductPrice(float newPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDto getProductByArtikul(String artikul) {
		Product prod = productRepo.findByArtikul(artikul);
		return productToProductDtoMapper(prod);
	}

	

}
