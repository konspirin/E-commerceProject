package application.converters;

import javax.persistence.AttributeConverter;

import application.datamembers.ImagesBox;



public class ImagesBoxConverter implements AttributeConverter<ImagesBox, String> {

	public static final String DELIMITER = ";";
	
	@Override
	public String convertToDatabaseColumn(ImagesBox imgBox) {
		
		return imgBox.getImg1()+DELIMITER + imgBox.getImg2()+DELIMITER + imgBox.getImg3()+DELIMITER
				+ imgBox.getImg4()+DELIMITER + imgBox.getThumbImg();
	}

	@Override
	public ImagesBox convertToEntityAttribute(String dbData) {
		String[] data = dbData.split(DELIMITER);
		
		return new ImagesBox(data[0], data[1], data[2], data[3], data[4]);
	}

}
