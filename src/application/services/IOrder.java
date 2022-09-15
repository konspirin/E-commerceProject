package application.services;
import java.time.LocalDate;
import java.util.List;

import application.api.ReturnCode;
import application.datamembers.*;
import application.dto.*;

public interface IOrder {
	ReturnCode addOrder(OrderDtoRequest orderDto);
	OrderDtoResponse getOrder(long orderId);
	List<OrderDtoResponse> getAllOrders();
	List<OrderDtoResponse> getAllOrdersCustomer(long customerId);
	List<OrderDtoResponse> getLastThreeOrdersCustomer(long customerId);
	List<OrderDtoResponse> getOrdersCustomerByPeriod(long customerId, LocalDate from, LocalDate to);
	ReturnCode orderChangeStatus(long orderId, String newStatus);
	ReturnCode removeOrderById(long orderId);
	
}
