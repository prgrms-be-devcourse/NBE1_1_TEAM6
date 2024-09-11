package programmers.coffee.product.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programmers.coffee.product.domain.Product;
import programmers.coffee.product.dto.NewProductDTO;
import programmers.coffee.product.dto.ProductDTO;
import programmers.coffee.product.repository.ProductRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;

	public ProductDTO save(NewProductDTO productDTO) {
		Product product = Product.from(productDTO);
		Product save = productRepository.save(product);
		log.info("Save : {}", save);
		return ProductDTO.from(save);
	}

	@Transactional
	public ProductDTO update(ProductDTO productDTO, UUID productId) {
		Product original = productRepository.findById(productId)
			.orElseThrow(() -> new NoSuchElementException("존재하는 상품이 없습니다."));

		log.info("Original : {}", original);
		log.info("Will be Update : {}", productDTO);

		original.updateProduct(productDTO);
		ProductDTO updated = ProductDTO.from(original);
		return updated;
	}

	public List<ProductDTO> getProducts() {

		// findAll 인자에 생성일자순 정렬 옵션 적용

		List<Product> findAll = productRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
		return findAll.stream()
			.map(ProductDTO::from)
			.toList();
	}

	/**
	 * 제품 삭제 => 이 제품 주문 내역들은? 지우는 것은 비즈니스상 옳지 않음
	 * 카테고리를 변경
	 */
	@Transactional
	public void deleteProduct(UUID productId) {
		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new NoSuchElementException("존재하지 않는 제품입니다."));
		product.deleteProduct();
	}
}
