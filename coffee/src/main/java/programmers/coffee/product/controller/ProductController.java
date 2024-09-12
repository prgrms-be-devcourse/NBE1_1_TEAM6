package programmers.coffee.product.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

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


	@GetMapping("/product/new")
	public ModelAndView newProduct(ModelAndView mav) {
		mav.addObject("newProduct", new NewProductDTO());
		mav.addObject("category", Category.values());
		mav.addObject("productStatus", ProductStatus.values());
		mav.setViewName("new-product");
		return mav;
	}

	@PostMapping("/product")
	public ResponseEntity<ProductDTO> saveProduct(@ModelAttribute NewProductDTO newProductDTO, @ModelAttribute MultipartFile file) throws
		IOException {
		log.info("===[ProductController.saveProduct] Start ===");

		log.info("===[ProductService.save] Start ===");
		ProductDTO responseDTO = productService.save(newProductDTO);
		log.info("===[ProductService.save] End ===");

		log.info("ProductDTO : {}", responseDTO);

		/**
		 * 파일 저장 로직
		 * 실제로는 외부 서버에 저장해야 한다.
		 */
		if (file.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String absolutePath = Paths.get("").toAbsolutePath().toString();
		String resource = absolutePath + "/" + responseDTO.getImgFile();
		log.info("Path = {}", resource);
		file.transferTo(new File(resource));
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
	@GetMapping()
	public ResponseEntity<?> getNonSoldoutProducts() {
		List<ProductDTO> products = productService.getNonSoldoutProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}
