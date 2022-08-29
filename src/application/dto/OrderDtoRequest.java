package application.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

		int orderId;
		private long customerId;
		private List<CartItemRequest> cartItems;
		private long timestampOrderDate;
		private Address deliveryAddr;
		
		public OrderDtoRequest(int orderId, long customerId, long orderDate, Address deliveryAddr) {
			super();
			this.orderId = orderId;
			this.customerId = customerId;
			this.timestampOrderDate = orderDate;
			this.deliveryAddr = deliveryAddr;
			this.cartItems = new ArrayList<>();
		}	
		
		public static OrderDtoRequest randomOrderDtoRequestCreater() {
			OrderDtoRequest res = new OrderDtoRequest(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE),
					ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE),
					ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE),
					new Address());
			res.setCartItems(randomListCartItems(10));
			return res;
		}
		
		private static List<CartItemRequest> randomListCartItems(int number) {
			List<CartItemRequest> res = new ArrayList<>();
			for(int i = 0; i<number; i++)
				res.add(new CartItemRequest(ThreadLocalRandom.current().nextLong(0, Integer.MAX_VALUE),
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
				// TODO Auto-generated catch block
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}





