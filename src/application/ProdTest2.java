package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import application.api.ReturnCode;
import application.dto.ProductDto;
import application.services.IProduct;
@SpringBootTest
class ProdTest2 {
	
	ProductDto prod = ProductDto.randomProductDto();
	@Autowired
	IProduct service;
	@Test
	void testAddProductTest() {
		assertEquals(ReturnCode.OK, service.addProduct(prod));
	}

}
