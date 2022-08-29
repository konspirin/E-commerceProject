package application.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.api.DtoConfig;
import application.datamembers.AttrName;
import application.datamembers.Category;
import application.datamembers.Collection;
import application.datamembers.Gender;
import application.datamembers.ImagesBox;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor


public class ProductDto {

		private long id;
		private String name;
		private String artikul;
		private String category;
//		private String collection;
//		private String gender;
		private double price;
		private double old_price;
		private ImagesBox imgBox;
		private int rating;
		private Map<String, String> attrValues;


//*************ProductDto random generator************************
public static ProductDto randomProductDto() {
	
	long randomId = ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE);
	String randomName = RandomLib.randomStringOfArray(DtoConfig.PRODUCT_NAMES);
	String randomArticle = "ART"+RandomLib.randomString(6);
//	String randomCollection = enumRandomValue(Collection.values());
//	String randomGender = enumRandomValue(Gender.values());
	String randomCategory = enumRandomValue(Category.values());
	double randomPrice = ThreadLocalRandom.current().nextDouble(DtoConfig.PRODUCT_MIN_PRICE, DtoConfig.PRODUCT_MAX_PRICE);
	double randomOld_price = randomPrice*1.3;
	ImagesBox randomImgBox = new ImagesBox("Basic"+RandomLib.randomString(20), "Basic"+RandomLib.randomString(20),
			"Basic"+RandomLib.randomString(20), "Basic"+RandomLib.randomString(20), "Basic"+RandomLib.randomString(20));
	int randomRating = ThreadLocalRandom.current().nextInt(5);
	Map<String, String> randomAttrValues = randomAttrValToMap();
	return new ProductDto(randomId, randomName, randomArticle, randomCategory, randomPrice, randomOld_price, randomImgBox, randomRating, randomAttrValues);
	
	}


private static Map<String, String> randomAttrValToMap() {
	Map<String, String> res = new HashMap<String, String>();
	AttrName [] names = AttrName.values();
//	int numAttr = ThreadLocalRandom.current().nextInt(names.length);
//	for(int i=0; i<=numAttr; i++)
		for(int i=0; i<names.length; i++)
	{
		switch (names[i].toString()){
		case "COLLECTION": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Collection.values()));
		}
		case "GENDER": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Gender.values()));
		}
		case "NEW_ARRIVAL": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.NEW_ARRIVAL));
		}
		
		default:
			res.putIfAbsent(names[i].toString(), RandomLib.randomString(6).toUpperCase());
		}
		
	}
	return res;
}


private static String enumRandomValue(Object [] array) {
	return array[ThreadLocalRandom.current().nextInt(array.length)].toString();
}

public static String ProductDtoToJson(ProductDto prodDto) {
	ObjectMapper mapper = new ObjectMapper();
	try {
		String res =  mapper.writeValueAsString(prodDto);
		System.out.println(res);
		return res;
	} catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return e.getMessage();
	}
}

}
