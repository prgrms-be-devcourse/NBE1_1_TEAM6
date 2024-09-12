package programmers.coffee.order.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import programmers.coffee.constant.OrderStatus;
import programmers.coffee.order.dto.OrderRequestDTO;

/**
 * MySQL에서 order는 예약어 => order로 저장하면 오류 발생
 */

@Entity
@Builder
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {

	@Id
	private UUID orderId;

	private String email;
	private String address;
	private String postCode;
	private OrderStatus orderStatus;
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
	@Builder.Default
	private List<OrderItem> orderItems = new ArrayList<>();

	@PrePersist
	public void prePersist() {
		this.orderId = UUID.randomUUID();
		this.createdAt = LocalDateTime.now();
		LocalDateTime today2PM = LocalDateTime.of(createdAt.toLocalDate(), LocalTime.of(14, 0));

		if (createdAt.isBefore(today2PM)) {
			this.orderStatus = OrderStatus.TODAY_DELIVERY;
		} else {
			this.orderStatus = OrderStatus.PENDING_DELIVERY;
		}
	}

	public static Order from(OrderRequestDTO requestDTO) {
		return Order.builder()
			.email(requestDTO.getEmail())
			.address(requestDTO.getAddress())
			.postCode(requestDTO.getPostCode())
			.build();
	}

	public void registerOrderItem(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
