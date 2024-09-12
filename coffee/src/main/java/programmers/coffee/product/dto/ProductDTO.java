package programmers.coffee.product.dto;

import java.io.IOException;
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
	private static String IMG_DIR="/Users/kimkahyun/Desktop/데브코스/1차프로젝트/git/NBE1_1_TEAM6/coffee/src/main/resources/static/img/";

	private Long productId;

	private String productName;

	private Category category;

	private ProductStatus productStatus;

	private Long price;

	private String description;

	private String imgPath;

	private Integer stock;

	public static ProductDTO from(Product product) {
		return ProductDTO.builder()
				.productId(product.getProductId())
				.productName(product.getProductName())
				.category(product.getCategory())
				.productStatus(product.getProductStatus())
				.price(product.getPrice())
				.description(product.getDescription())
				.imgPath(IMG_DIR + product.getProductId() + ".jpg")
				.stock(product.getStock())
				.build();
	}
}