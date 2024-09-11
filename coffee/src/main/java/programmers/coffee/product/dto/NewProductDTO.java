package programmers.coffee.product.dto;

import lombok.Data;
import programmers.coffee.constant.Category;

@Data
public class NewProductDTO {
	private String productName;
	private Category category;
	private Long price;
	private String description;
}
