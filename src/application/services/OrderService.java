package application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import application.api.ReturnCode;
import application.datamembers.Status;
import application.dto.CartItemRequest;
import application.dto.CartItemResponse;
import application.dto.OrderDtoRequest;
import application.dto.OrderDtoResponse;
import application.entities.CartItem;
import application.entities.Customer;
import application.entities.Order;
import application.entities.Product;
import application.repositories.CartItemRepository;
import application.repositories.CustomerRepository;
import application.repositories.OrderRepository;
import application.repositories.ProductRepository;

@Service
public class OrderService implements IOrder {
	
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	CartItemRepository cartItemRepo;
	@Autowired
	ProductRepository productRepo;

	@Override
	public ReturnCode addOrder(OrderDtoRequest orderDto) {
		Order order = new Order(customerRepo.findById(orderDto.getCustomerId()).orElse(new Customer()), orderDto.getTimestampOrderDate(),
				orderDto.getDeliveryAddr(), Status.PAYD);
		orderRepo.save(order);

		orderDto.getCartItems().forEach(item -> cartItemRepo.save(cartItemRequestToCartItem(item, order.getOrderId())));
		
		return ReturnCode.OK;
	}

	private CartItem cartItemRequestToCartItem(CartItemRequest item, long id) {
		Product prod = productRepo.findById(item.getProdId()).orElse(null);
		CartItem res = new CartItem(prod.getArtikul(),
		 item.getColor(), item.getSize(), item.getNumber(), prod.getImgBox().getThumbImg(), orderRepo.findById(id).orElse(null));
		return res;
	}
	@Override
	public OrderDtoResponse getOrder(long orderId) {
		if(!orderRepo.existsById(orderId))
//			return null;
			return new OrderDtoResponse(1234, 5678, Status.CANCELED, null);
		Order temp = orderRepo.findById(orderId).orElse(null);
			return orderToOrderDtoResponseMapper(temp);
		
	}

	private OrderDtoResponse orderToOrderDtoResponseMapper(Order temp) {
		
		return new OrderDtoResponse(temp.getOrderId(), temp.getTimestampOrderDate(), temp.getOrderStatus(),
				ListCartItemToListCartItemResponse(temp.getOrderProducts()));
	}

	private List<CartItemResponse> ListCartItemToListCartItemResponse(List<CartItem> orderProducts) {
		
		return orderProducts.stream().map(item -> cartItemToCartItemResponseMapper(item)).collect(Collectors.toList());
	}

	private CartItemResponse cartItemToCartItemResponseMapper(CartItem item) {
		CartItemResponse res = new CartItemResponse(item.getArtikul(), item.getColor(), item.getSize(),
				item.getNumber(), item.getThumbImg(), item.getOrder().getOrderId());
		return res;
	}

	@Override
	public List<OrderDtoResponse> getAllOrdersCustomer(long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDtoResponse> getLastThreeOrdersCustomer(long customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDtoResponse> getOrdersCustomerByPeriod(long customerId, LocalDate from, LocalDate to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnCode orderChangeStatus(long orderId, String newStatus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDtoResponse> getAllOrders() {
		List<Order> orders = orderRepo.findAll();
		List<OrderDtoResponse> res = orders.stream().map(o -> orderToOrderDtoResponseMapper(o))
				.collect(Collectors.toList());
		return res;
	}

}
