package application;

import application.dto.ArticleDto;
import application.dto.OrderDtoRequest;
import application.dto.ProductDto;
import application.services.ProductService;

public class ProductDtoTestAppl {

	public static void main(String[] args) {
//		ProductDto prod = ProductDto.randomProductDto();
//		System.out.println(prod);
//		ProductDto.ProductDtoToJson(prod);
//		ArticleDto art = ArticleDto.randomArticleDto();
//		System.out.println(art);
//		String res = ArticleDto.ArticleDtoToJson(art);
//		System.out.println(res);
		
		OrderDtoRequest order = OrderDtoRequest.randomOrderDtoRequestCreater();
		System.out.println(order);
		System.out.println(OrderDtoRequest.orderDtoRequestToJson(order));
	}

}
