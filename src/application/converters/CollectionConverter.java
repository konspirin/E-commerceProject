package application.converters;

import javax.persistence.AttributeConverter;

import application.datamembers.Collection;

public class CollectionConverter implements AttributeConverter<Collection, String> {

	@Override
	public String convertToDatabaseColumn(Collection collection) {
		return collection.toString();
	}

	@Override
	public Collection convertToEntityAttribute(String dbData) {
		return Collection.valueOf(dbData);
	}

}
