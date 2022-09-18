package application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.api.ReturnCode;
import application.dto.OrderDtoRequest;
import application.dto.OrderDtoResponse;
import application.services.IOrder;

@RestController
@CrossOrigin
public class OrderController {
	@Autowired
	IOrder orderService;
	
	@Transactional
	@PostMapping(value = "/order_add")
	ReturnCode addOrder(@RequestBody OrderDtoRequest orDtoReq){
//		if(orDtoReq == null) return ReturnCode.ARTICLE_NOT_FOUND;
//		return new ResponseEntity<OrderDtoRequest>(HttpStatus.OK);
		return orderService.addOrder(orDtoReq);
	}
	
	@GetMapping(value = "/order_by_id_get")
	OrderDtoResponse getOrder(@RequestParam long orderId) {
		return orderService.getOrder(orderId);
	}
	@GetMapping("/all_orders_get")
	List<OrderDtoResponse> getAllOrders(){
		return orderService.getAllOrders();
	}
	@DeleteMapping("/order_by_id_remove")
	ReturnCode removeOrderById(@RequestParam long orderId) {
		return orderService.removeOrderById(orderId);
	}
}
