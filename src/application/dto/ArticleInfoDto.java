package application.dto;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ArticleInfoDto {

	private int id;
	private String title;
	private String thumbImgUrl;
	private LocalDate dateModified;
}
