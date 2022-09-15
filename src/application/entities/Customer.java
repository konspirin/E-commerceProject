package application.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


import application.converters.AddressConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "customers")

public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	private String firstName;
	private String lastName;
	private String avatarImgUrl;
	private String email;
	private String phone;
	@Convert(converter = AddressConverter.class)
	private Address address;
	@OneToMany(cascade = CascadeType.REMOVE)
	private List<Order>orders;
	private long cardNumber;
	

	
	
}
