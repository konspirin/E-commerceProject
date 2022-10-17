package application.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.api.DtoConfig;
import application.datamembers.AttrName;
import application.datamembers.Brand;
import application.datamembers.Category;
import application.datamembers.Collection;
import application.datamembers.Gender;
import application.datamembers.ImagesBox;
import application.datamembers.Season;
import application.datamembers.Style;
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
		private int discount;
		private ImagesBox imgBox;
		private int rating;
		private Map<String, String> attrValues;


//*************ProductDto random generator************************
public static ProductDto randomProductDto() {
	
	long randomId = ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE);
	String randomName = RandomLib.randomStringOfArray(DtoConfig.PRODUCT_NAMES);
	String randomArticle = "ART"+RandomLib.randomString(6);
	String randomCategory = enumRandomValue(Category.values());
	double randomPrice = ThreadLocalRandom.current().nextDouble(DtoConfig.PRODUCT_MIN_PRICE, DtoConfig.PRODUCT_MAX_PRICE);
	int randomDisc = ThreadLocalRandom.current().nextInt(0,45);
	ImagesBox randomImgBox = new ImagesBox("Basic"+RandomLib.randomString(20), "Basic"+RandomLib.randomString(20),
			"Basic"+RandomLib.randomString(20), "Basic"+RandomLib.randomString(20), "Basic"+RandomLib.randomString(20));
	int randomRating = ThreadLocalRandom.current().nextInt(5);
	Map<String, String> randomAttrValues = randomAttrValToMap();
	return new ProductDto(randomId, randomName, randomArticle, randomCategory, randomPrice, randomDisc, randomImgBox, randomRating, randomAttrValues);
	
	}


private static Map<String, String> randomAttrValToMap() {
	Map<String, String> res = new HashMap<String, String>();
	AttrName [] names = AttrName.values();

		for(int i=0; i<names.length; i++)
	{
		switch (names[i].toString()){
		case "COLLECTION": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Collection.values()));
		}
		case "GENDER": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Gender.values()));
		}
		case "BRAND": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Brand.values()));
		}
		case "STYLE": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Style.values()));
		}
		case "SEASON": {
			res.putIfAbsent(names[i].toString(), enumRandomValue(Season.values()));
		}
		case "NEW_ARRIVAL": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
		}
		case "OUR_FAVORITE": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
		}
		case "NEW_COLLECTION": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
		}
		case "TREND": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
		}
		case "NEW_NAME": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
		}
		case "LUX": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
		}
		case "EXCLUSIVE": {
			res.putIfAbsent(names[i].toString(), RandomLib.randomStringOfArray(DtoConfig.USERS_CHOICE));
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
		e.printStackTrace();
		return e.getMessage();
	}
}

}
