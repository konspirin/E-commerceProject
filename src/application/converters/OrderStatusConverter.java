package application.converters;

import javax.persistence.AttributeConverter;

import application.datamembers.Gender;
import application.datamembers.Status;

public class OrderStatusConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status status) {
		return status.toString();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		return Status.valueOf(dbData);
	}

}
