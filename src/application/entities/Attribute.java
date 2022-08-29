package application.entities;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import application.converters.AttrNameConverter;
import application.datamembers.AttrName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
@Entity
public class Attribute {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Convert(converter = AttrNameConverter.class)
	private AttrName attrName;
	private String type;
	private boolean required;
	
	
	public Attribute(AttrName name, String type, boolean required) {
		super();
		this.attrName = name;
		this.type = type;
		this.required = required;
	}
	

}
