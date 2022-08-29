package application.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "attribute_values")
public class AttrValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Product product;
	@OneToOne
	private Attribute attribute;
	private String value;
	
	public AttrValue(Product product, Attribute attribute, String value) {
		super();
		this.product = product;
		this.attribute = attribute;
		this.value = value;
	}
	
	
}
