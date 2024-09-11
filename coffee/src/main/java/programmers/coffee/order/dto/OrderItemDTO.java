package programmers.coffee.order.dto;

import lombok.Builder;
import lombok.Data;
import programmers.coffee.order.domain.OrderItem;

@Data
@Builder
public class OrderItemDTO {
	private String productName;

	private String category;

	private Long price;

	private Integer quantity;

	public static OrderItemDTO from(OrderItem orderItem) {
		return OrderItemDTO.builder()
			.productName(orderItem.getProduct().getProductName())
			.category(orderItem.getCategory())
			.price(orderItem.getPrice())
			.quantity(orderItem.getQuantity())
			.build();
	}
}
