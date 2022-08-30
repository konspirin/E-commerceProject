package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ArticleInfoDto {

	private long id;
	private String title;
	private String thumbImgUrl;
	private Long timestampDateMod;
}
