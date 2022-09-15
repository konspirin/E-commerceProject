package application.dto;


import java.util.List;

import application.datamembers.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDtoResponse {
	long orderId;
	//long customerId;
	long timestamp;
	Status status;
	double deliveryCost;
	List<CartItemResponse> items;
}
