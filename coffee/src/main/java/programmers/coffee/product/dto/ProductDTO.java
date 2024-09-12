package programmers.coffee.product.dto;

import org.springframework.beans.factory.annotation.Value;

import lombok.Builder;
import lombok.Data;
import programmers.coffee.constant.Category;
import programmers.coffee.constant.ProductStatus;
import programmers.coffee.product.domain.Product;

@Data
@Builder
public class ProductDTO {
	@Value("${file.dir}")
	private static String IMG_DIR;

	private Long productId;

	private String productName;

	private Category category;

	private ProductStatus productStatus;

	private Long price;

	private String description;

	private String imgFile;

	public static ProductDTO from(Product product) {
		return ProductDTO.builder()
			.productId(product.getProductId())
			.productName(product.getProductName())
			.category(product.getCategory())
			.productStatus(product.getProductStatus())
			.price(product.getPrice())
			.description(product.getDescription())
			.imgFile(IMG_DIR + product.getProductId() + ".jpg")
			.build();
	}
}
