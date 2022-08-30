package application.entities;

import javax.persistence.Column;
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
	private long id;
	private String title;
	private String body;
	@Column(name="images")
	private String images;
	@Column(name="thumb_img")
	private String thumbImg;
	@Column(name="timestamp")
	private Long timestampDateMod;;
}
