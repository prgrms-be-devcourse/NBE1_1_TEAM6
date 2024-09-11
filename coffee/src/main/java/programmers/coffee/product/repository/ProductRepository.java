package programmers.coffee.product.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import programmers.coffee.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByProductNameContaining(String productName);
}
