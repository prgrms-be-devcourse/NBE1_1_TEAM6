package programmers.coffee.order.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import programmers.coffee.product.domain.Product;

@Entity
@Getter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	/**
	 * Order 삭제 시 OrderItem도 삭제될 수 있게 CACADE 옵션 추가
	 * JPA의 Cascade로 삭제할 시, 실제 DB 상의 CASCADE가 아니라, delete 순서가 바뀐다.
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Order order;

	// 차후 주문 취소시, OrderItem도 삭제되어야 하므로 cascade 적용할 것.

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	private String category;

	private Long price;

	private Integer quantity;

	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();
	}

	public static OrderItem of(Product product, Integer quantity, Order order) {
		return OrderItem.builder()
			.order(order)
			.product(product)
			// Enum 타입 변경 로직
			// .category(product.getCategory())
			.price(product.getPrice() * quantity)
			.quantity(quantity)
			.build();
	}
}
