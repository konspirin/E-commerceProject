package application.converters;

import javax.persistence.AttributeConverter;

import application.datamembers.Gender;

public class GenderConvertor implements AttributeConverter<Gender, String> {

	@Override
	public String convertToDatabaseColumn(Gender gender) {	
		return gender.toString();
	}

	@Override
	public Gender convertToEntityAttribute(String dbData) {
		return Gender.valueOf(dbData);
	}

}
