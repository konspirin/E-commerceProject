package application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResponsePageProdBaseInfo {
	int totalItem;
	int pageNum;
	int totalPages;
	List<ProductBaseInfoDto> list;	
}
