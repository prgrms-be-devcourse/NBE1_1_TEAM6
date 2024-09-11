package programmers.coffee.product.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import programmers.coffee.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContaining(String productName);

    List<Product> findByPriceBetween(Long minPrice, Long maxPrice);

    @Query("SELECT p FROM Product p WHERE p.stock != 0")
    List<Product> findNonSoldOut();
}
