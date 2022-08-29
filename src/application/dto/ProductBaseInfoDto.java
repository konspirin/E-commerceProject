package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ProductBaseInfoDto {
	private long prodId;
	private String name;
	private String artikul;
	private double price;
	private double oldPrice;
	private String ThumbUrl;
	private int rating;
}
