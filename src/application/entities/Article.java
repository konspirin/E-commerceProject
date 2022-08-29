package application.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="blog_articles")

public class Article {
	@Id
	private int id;
	private String title;
	private String body;
	private String imgUrl;
	private String thumbImgUrl;
	private LocalDate dateModified;
}
