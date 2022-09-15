package application.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import application.converters.AddressConverter;
import application.converters.OrderStatusConverter;
import application.datamembers.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = "orderProducts")
@Entity(name = "orders")

public class Order {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	long orderId;
	@Column(name = "customer_id")
	private long customerId;
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Column(name = "order_products")
	private List<CartItem> orderProducts = new ArrayList<>();
//	@Column(name = "timestamp")
	private long timestampOrderDate;
	@Convert(converter = AddressConverter.class)
//	@Column(name = "delivery_address")
	private Address deliveryAddr;
	@Convert(converter = OrderStatusConverter.class)
//	@Column(name = "status")
	private Status orderStatus;
	@Column(name = "delivery_cost")
	double deliveryCost;
	
	
	public Order(long customerId, long timestampOrderDate, Address deliveryAddr,
			Status orderStatus, double deliveryCost) {
		super();
		this.customerId = customerId;
		this.timestampOrderDate = timestampOrderDate;
		this.deliveryAddr = deliveryAddr;
		this.orderStatus = orderStatus;
		this.deliveryCost = deliveryCost;
	}
	

	
}


