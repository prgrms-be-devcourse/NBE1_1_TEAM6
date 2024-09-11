package programmers.coffee.product.dto;

import lombok.Builder;
import lombok.Data;
import programmers.coffee.product.domain.Product;

@Data
@Builder
public class ProductDTO {

	private Long productId;

	private String productName;

	private String category;

	private Long price;

	private String description;

	public static ProductDTO from(Product product) {
		return ProductDTO.builder()
			.productId(product.getProductId())
			.productName(product.getProductName())
			// Enum 타입으로 바꾸는 작업 필요
			// .category(product.getCategory())
			.price(product.getPrice())
			.description(product.getDescription())
			.build();
	}
}
