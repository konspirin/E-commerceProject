package application.converters;

import javax.persistence.AttributeConverter;

import application.datamembers.Category;

public class CategoryConverter implements AttributeConverter<Category, String> {

	@Override
	public String convertToDatabaseColumn(Category category) {
		return category.toString();
	}

	@Override
	public Category convertToEntityAttribute(String dbData) {
		return Category.valueOf(dbData);
	}

}
