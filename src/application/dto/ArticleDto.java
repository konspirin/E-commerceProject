package application.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ArticleDto {

	private int id;
	private String title;
	private String body;
	private String imgUrl;
	private String thumbImgUrl;
//	private LocalDate dateModified;
	
	public static ArticleDto randomArticleDto() {
		ArticleDto res = new ArticleDto(ThreadLocalRandom.current().nextInt(0,10000),
				RandomLib.randomString(10),
				"Lorem Ipsum is simply dummy text of the printing and typesetting industry."
				+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s,",
				"http://"+RandomLib.randomString(10),
				"http://"+RandomLib.randomString(10)
//				LocalDate.now()
				);
		return res;
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
