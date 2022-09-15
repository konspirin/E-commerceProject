package application.converters;

import javax.persistence.AttributeConverter;

import application.entities.Address;

public class AddressConverter implements AttributeConverter<Address, String> {

	public static final String DELIMETER = ";";

	@Override
	public String convertToDatabaseColumn(Address address) {
		
		return address.getCountry()+DELIMETER + address.getZip()+DELIMETER
				+address.getAddr();
	}

	@Override
	public Address convertToEntityAttribute(String dbData) {
		
		String[] temp = dbData.split(DELIMETER);
		return new Address(temp[0], Integer.parseInt(temp[1]), temp[2]);
	}

}
