package programmers.coffee.product.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programmers.coffee.product.domain.Product;
import programmers.coffee.product.dto.NewProductDTO;
import programmers.coffee.product.dto.ProductDTO;
import programmers.coffee.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	@Value("${file.dir}")
	private String fileDir;

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
	public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO, @PathVariable Long productId) {
		log.info("===[ProductController.updateProduct] Start ===");

		log.info("===[ProductService.update] Start ===");

		ProductDTO updated = productService.update(productDTO, productId);
		log.info("===[ProductService.update] End ===");

		log.info("Updated : {}", updated);

		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	//전체 상품 조회
	@GetMapping("/product")
	public ResponseEntity<Page<ProductDTO>> getProducts(@RequestParam(value="page", defaultValue="0") int page) {
		log.info("=== [ProductController.getProducts] Start ===");
		log.info("=== [ProductService.getProducts] Start ===");
		Page<ProductDTO> products = productService.getProducts(page);
		log.info("=== [ProductService.getProducts] End ===");
		log.info("Products : {}", products);
		log.info("=== [ProductController.getProducts] End ===");
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
		productService.deleteProduct(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	//상품 이름으로 검색
	@GetMapping("/search/name")
	public ResponseEntity<?> searchByProductName(@RequestParam String productName) {
		List<ProductDTO> products = productService.searchByProductName(productName);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	//가격으로 검색
	@GetMapping("/search/price")
	public ResponseEntity<?> searchByPrice(@RequestParam Long minPrice, @RequestParam Long maxPrice) {
		if (minPrice > maxPrice) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		List<ProductDTO> products = productService.searchByPrice(minPrice, maxPrice);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	//품절된 상품 제외하고 조회
	@GetMapping("/search/nonsoldout")
	public ResponseEntity<?> getNonSoldoutProducts() {
		List<ProductDTO> products = productService.getNonSoldoutProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
