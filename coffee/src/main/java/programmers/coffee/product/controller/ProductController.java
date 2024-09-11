package programmers.coffee.product.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programmers.coffee.product.dto.NewProductDTO;
import programmers.coffee.product.dto.ProductDTO;
import programmers.coffee.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	@PostMapping("/product")
	public ResponseEntity<ProductDTO> saveProduct(@RequestBody NewProductDTO newProductDTO) {
		log.info("===[ProductController.saveProduct] Start ===");

		log.info("===[ProductService.save] Start ===");
		ProductDTO responseDTO = productService.save(newProductDTO);
		log.info("===[ProductService.save] End ===");

		log.info("ProductDTO : {}", responseDTO);
		log.info("===[ProductController.saveProduct] End ===");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/product/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable UUID productId) {
		log.info("===[ProductController.updateProduct] Start ===");

		log.info("===[ProductService.update] Start ===");

		ProductDTO updated = productService.update(productDTO, productId);
		log.info("===[ProductService.update] End ===");

		log.info("Updated : {}", updated);

		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@GetMapping("/product")
	public ResponseEntity<List<ProductDTO>> getProducts() {
		log.info("=== [ProductController.getProducts] Start ===");
		log.info("=== [ProductService.getProducts] Start ===");
		List<ProductDTO> products = productService.getProducts();
		log.info("=== [ProductService.getProducts] End ===");
		log.info("Products : {}", products);
		log.info("=== [ProductController.getProducts] End ===");
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable UUID productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
