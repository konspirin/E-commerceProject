package application.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	//***********************MAPPERS***********************
	private CartItem cartItemRequestToCartItemMapper(CartItemRequest item, long id) {
		Product prod = productRepo.findById(item.getProdId()).orElse(null);
		CartItem res = new CartItem(prod.getArtikul(),
		 item.getColor(), item.getSize(), item.getNumber(), prod.getPrice(),
		 prod.getImgBox().getThumbImg(), orderRepo.findById(id).orElse(null));
		System.out.println(res);
		return res;
	}
	
	private OrderDtoResponse orderToOrderDtoResponseMapper(Order order) {	
		return new OrderDtoResponse(order.getOrderId(), order.getTimestampOrderDate(), order.getOrderStatus(),
				order.getDeliveryCost(), ListCartItemToListCartItemResponseMapper(order.getOrderProducts()));
	}

	private List<CartItemResponse> ListCartItemToListCartItemResponseMapper(List<CartItem> orderProducts) {	
		return orderProducts.stream().map(item -> cartItemToCartItemResponseMapper(item)).collect(Collectors.toList());
	}

	private CartItemResponse cartItemToCartItemResponseMapper(CartItem item) {
		CartItemResponse res = new CartItemResponse(item.getArtikul(), item.getColor(), item.getSize(),
				item.getNumber(), item.getThumbImg(), item.getPrice());
		return res;
	}
	
	//********************SERVICE**************************
	@Override
	@Transactional
	public ReturnCode addOrder(OrderDtoRequest orderDto) {
	Order order;
		try {
			 order = new Order(orderDto.getCustomerId(), orderDto.getTimestampOrderDate(),
					orderDto.getDeliveryAddr(), Status.PAYD, orderDto.getDeliveryCost());
			orderRepo.save(order);
		} catch (Exception e) {
			return ReturnCode.WRONG_DATE;
		}

		try {
			orderDto.getCartItems().forEach(item -> 
			cartItemRepo.save(cartItemRequestToCartItemMapper(item, order.getOrderId())));
//			orderDto.getCartItems().stream().map(item ->
//			cartItemRepo.save(cartItemRequestToCartItemMapper(item, order.getOrderId())));
		} catch (Exception e) {
			return ReturnCode.ORDER_NOT_FOUND;
		}
		
		return ReturnCode.OK;
	}

	
	@Override
	public OrderDtoResponse getOrder(long orderId) {
		if(!orderRepo.existsById(orderId))
//			return null;
			return new OrderDtoResponse(1234, 5678, Status.CANCELED,100.0, null);
		Order temp = orderRepo.findById(orderId).orElse(null);
			return orderToOrderDtoResponseMapper(temp);
		
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

	@Override
	@Transactional
	public ReturnCode removeOrderById(long orderId) {
		Order order = orderRepo.findById(orderId).orElse(null);
		if(order != null) {
			List <CartItem> listItems = order.getOrderProducts();
			listItems.forEach(item -> cartItemRepo.deleteById(item.getId()));
			orderRepo.deleteById(orderId);
			return ReturnCode.OK;
		}
		return ReturnCode.ORDER_NOT_FOUND;
	}

}
