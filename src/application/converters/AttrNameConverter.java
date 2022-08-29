package application.converters;

import javax.persistence.AttributeConverter;

import application.datamembers.AttrName;

public class AttrNameConverter implements AttributeConverter<AttrName, String> {

	@Override
	public String convertToDatabaseColumn(AttrName attrName) {
		return attrName.toString();
	}

	@Override
	public AttrName convertToEntityAttribute(String dbData) {
		return AttrName.valueOf(dbData);
	}

}
