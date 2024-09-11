package programmers.coffee.product;

import static org.assertj.core.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import programmers.coffee.product.domain.Product;
import programmers.coffee.product.dto.NewProductDTO;
import programmers.coffee.product.dto.ProductDTO;
import programmers.coffee.product.repository.ProductRepository;

@SpringBootTest
public class ProductTest {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	EntityManager em;

	Product initProduct;

	@BeforeEach
	public void beforeEach() {
		NewProductDTO productDTO = new NewProductDTO();
		productDTO.setProductName("Test Coffee Bean");
		productDTO.setCategory("Coffee Bean");
		productDTO.setDescription("This is Test Coffee Bean");
		productDTO.setPrice(10000L);
		initProduct = Product.from(productDTO);

		productRepository.save(initProduct);
	}

	@Test
	@Transactional
	public void 제품_저장() {
		Product findProduct = productRepository.findById(initProduct.getProductId())
			.orElseThrow(() -> new NoSuchElementException("저장되지 않음"));

		assertThat(findProduct).isEqualTo(initProduct);
	}

	@Test
	@Transactional
	public void 제품_수정() {
		ProductDTO update = ProductDTO.builder()
			.productId(initProduct.getProductId())
			.productName("Updated Test Coffee Bean")
			.category("Updated Coffee Bean")
			.price(9990L)
			.description("Updated Test Coffee Bean.")
			.build();

		initProduct.updateProduct(update);
		em.flush();

		Product updated = productRepository.findById(initProduct.getProductId())
			.orElseThrow(() -> new NoSuchElementException("저장되지 않음"));

		assertThat(updated.getProductName()).isEqualTo("Updated Test Coffee Bean");
		assertThat(updated.getCategory()).isEqualTo("Updated Coffee Bean");
		assertThat(updated.getPrice()).isEqualTo(9990L);
		assertThat(updated.getDescription()).isEqualTo("Updated Test Coffee Bean.");
	}

	@Transactional
	@Test
	public void 제품_삭제() {
		initProduct.deleteProduct();
		em.flush();

		assertThat(initProduct.getCategory()).isEqualTo("판매중지");
	}
}
