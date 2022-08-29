package application.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Address {

	private String city;
	private String street;
	private int home;
	private int apt;
}
