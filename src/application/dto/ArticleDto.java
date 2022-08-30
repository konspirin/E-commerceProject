package application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.api.DtoConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ArticleDto {

	private long id;
	private String title;
	private String body;
	private List <String> images;
	private String thumbImg;
	private Long timestampDateMod;
	
	public static ArticleDto randomArticleDto() {
		
		ArticleDto res = new ArticleDto(ThreadLocalRandom.current().nextLong(0,10000),
				RandomLib.randomString(10),
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry."
				+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,",
				getListRandomImages(),
				"http://"+RandomLib.randomString(10),
				ThreadLocalRandom.current().nextLong(0,Long.MAX_VALUE));
				
		return res;
	}
	private static List<String> getListRandomImages() {
		int n = ThreadLocalRandom.current().nextInt(1,10);
		List<String> list = new ArrayList<>();
		for(int i=0; i<n; i++)
			list.add("http://"+RandomLib.randomString(10));
		return list;
	}
	public static String ArticleDtoToJson(ArticleDto artDto) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String res =  mapper.writeValueAsString(artDto);
			System.out.println(res);
			return res;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
