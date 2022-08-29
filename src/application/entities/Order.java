package application.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import application.converters.AddressConverter;
import application.converters.OrderStatusConverter;
import application.datamembers.Status;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity(name = "orders")

public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	long orderId;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy = "order")
	private List<CartItem> orderProducts;
	private long timestampOrderDate;
	@Convert(converter = AddressConverter.class)
	private Address deliveryAddr;
	@Convert(converter = OrderStatusConverter.class)
	private Status orderStatus;
	
	
	public Order(Customer customer, long timestampOrderDate, Address deliveryAddr,
			Status orderStatus) {
		super();
		this.customer = customer;
		this.timestampOrderDate = timestampOrderDate;
		this.deliveryAddr = deliveryAddr;
		this.orderStatus = orderStatus;
	}
	

	
}


