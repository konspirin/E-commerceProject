package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartItemResponse {

	private String artikul;
	private String color;
	private String size;
	private float number;
	private String thumbImg;
	private long orderId;
	
	
	
	
}
