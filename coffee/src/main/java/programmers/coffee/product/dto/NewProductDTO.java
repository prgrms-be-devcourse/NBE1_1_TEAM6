package programmers.coffee.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import programmers.coffee.constant.Category;
import programmers.coffee.constant.ProductStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProductDTO {
	private String productName;
	private Category category;
	private Long price;
	private String description;
	private Integer stock;
	private ProductStatus productStatus;
}
