package programmers.coffee.product.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programmers.coffee.constant.Category;
import programmers.coffee.constant.ProductStatus;
import programmers.coffee.product.dto.NewProductDTO;
import programmers.coffee.product.dto.ProductDTO;
import programmers.coffee.product.service.ProductService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	@PostMapping("/product")
	public ResponseEntity<ProductDTO> saveProduct(@ModelAttribute NewProductDTO newProductDTO, @ModelAttribute MultipartFile file) throws
			IOException {
		ProductDTO responseDTO = productService.save(newProductDTO, file);

		if (file.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/product/{productId}")
	public ResponseEntity<ProductDTO> updateProduct(@ModelAttribute NewProductDTO productDTO, @ModelAttribute MultipartFile file, @PathVariable Long productId) throws
			IOException {
		ProductDTO updated = productService.update(productDTO, file, productId);

		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	//전체 상품 조회
	@GetMapping("/product")
	public ResponseEntity<Page<ProductDTO>> getProducts(@RequestParam(value="page", defaultValue="0") int page) {
		Page<ProductDTO> products = productService.getProducts(page);
		log.info("Products : {}", products);
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

	//판매 중인 상품만 조회
	@GetMapping("/search/sale")
	public ResponseEntity<?> getNonSoldoutProducts() {
		List<ProductDTO> products = productService.getNonSoldoutProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
