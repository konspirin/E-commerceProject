package application.dto;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import application.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString

public class OrderDtoRequest {

		private long orderId;
		private long customerId;
		private List<CartItemRequest> cartItems;
		private long timestampOrderDate;
		private Address deliveryAddr;
		private double deliveryCost;
		
		public OrderDtoRequest(long orderId, long customerId, long orderDate, Address deliveryAddr, double deliveryCost) {
			super();
			this.orderId = orderId;
			this.customerId = customerId;
			this.timestampOrderDate = orderDate;
			this.deliveryAddr = deliveryAddr;
			this.deliveryCost = deliveryCost;
			this.cartItems = new ArrayList<>();
		}	
		
		public static OrderDtoRequest randomOrderDtoRequestCreater() {
			OrderDtoRequest res = new OrderDtoRequest(		
					ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE),
					ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE),
					ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE),
					new Address("England", 12354, "Downing street, 10"),
					ThreadLocalRandom.current().nextDouble(0, 10000));
			res.setCartItems(randomListCartItems(3));
			return res;
		}
		
		private static List<CartItemRequest> randomListCartItems(int number) {
			List<CartItemRequest> res = new ArrayList<>();
			for(int i = 0; i<number; i++)
				res.add(new CartItemRequest(
						21,
//						ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE),
						 RandomLib.randomString(6),
						 RandomLib.randomString(6),
						 ThreadLocalRandom.current().nextInt(0, 100)));
			
			return res;
		}

		public static String orderDtoRequestToJson(OrderDtoRequest orDtoReq) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				String res =  mapper.writeValueAsString(orDtoReq);
				System.out.println(res);
				return res;
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
		
		public static OrderDtoRequest JsonToOrderDtoRequest(String json) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				OrderDtoRequest res =  mapper.readValue(json, OrderDtoRequest.class);
				System.out.println(res);
				return res;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}





