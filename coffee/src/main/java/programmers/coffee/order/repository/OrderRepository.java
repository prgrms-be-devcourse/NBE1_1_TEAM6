package programmers.coffee.order.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import programmers.coffee.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	/**
	 * N+1 문제 해결을 위한 Fetch Join 방식 적용
	 */

	@Query("SELECT o FROM Order o " +
		"JOIN FETCH o.orderItems oi " +
		"JOIN FETCH oi.product " +
		"WHERE o.email = :email " +
		"ORDER BY o.createdAt ASC")
	List<Order> findByEmail(String email, Sort sort);
	/**
	 * 주문정보
	 * 주문제품
	 * Order -> OrderItem -> Product
	 *
	 */
}
