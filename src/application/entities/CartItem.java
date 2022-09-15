package application.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
@Table(name = "cart_item")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String artikul;
	private String color;
	private String size;
	private int number;
	private double price;
	@Column(name = "thumb_img")
	private String thumbImg;
	@ManyToOne
	private Order order;
	
	
	public CartItem(String artikul, String color, String size, int number, double price, String thumbImg, Order order) {
		super();
		this.artikul = artikul;
		this.color = color;
		this.size = size;
		this.number = number;
		this.price = price;
		this.thumbImg = thumbImg;
		this.order = order;
	}
	
	
}
