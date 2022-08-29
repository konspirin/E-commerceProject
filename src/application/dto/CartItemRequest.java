package application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartItemRequest {
	private long prodId;
	private String color;
	private String size;
	private int number;
}
