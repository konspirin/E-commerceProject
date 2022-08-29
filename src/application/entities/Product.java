package application.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import application.converters.CategoryConverter;
import application.converters.CollectionConverter;
import application.converters.GenderConvertor;
import application.converters.ImagesBoxConverter;
import application.datamembers.Category;
import application.datamembers.Collection;
import application.datamembers.Gender;
import application.datamembers.ImagesBox;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = "attrValues")
@Entity(name = "product")
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String artikul;
	@Convert(converter = CategoryConverter.class)
	private Category category;
//	@Convert (converter = CollectionConverter.class)
//	private Collection collection;
//	@Convert(converter = GenderConvertor.class)
//	private Gender gender;
	private double price;
	private double old_price;
	@Column(length = Integer.MAX_VALUE)
	@Convert(converter = ImagesBoxConverter.class)
	private ImagesBox imgBox;
	private int rating;
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private Set<AttrValue> attrValues = new HashSet<>();
	
	
	public Product(String name, String artikul, Category category, double price,
			double old_price, ImagesBox imgBox, int rating) {
		super();
		this.name = name;
		this.artikul = artikul;
		this.category = category;
//		this.collection = collection;
//		this.gender = gender;
		this.price = price;
		this.old_price = old_price;
		this.imgBox = imgBox;
		this.rating = rating;
	}
}

