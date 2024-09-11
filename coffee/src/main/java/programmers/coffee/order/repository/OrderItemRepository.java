package programmers.coffee.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import programmers.coffee.order.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
