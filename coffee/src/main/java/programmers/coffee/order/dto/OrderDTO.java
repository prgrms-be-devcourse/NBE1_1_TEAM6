package programmers.coffee.order.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import programmers.coffee.order.domain.Order;

@Data
@Builder
public class OrderDTO {

	private UUID orderId;
	private String email;
	private String address;
	private String postCode;
	private String orderStatus;
	private List<OrderItemDTO> orderItems = new ArrayList<>();

	public static OrderDTO from(Order order) {
		return OrderDTO.builder()
			.orderId(order.getOrderId())
			.email(order.getEmail())
			.address(order.getAddress())
			.postCode(order.getPostCode())
			.orderStatus(order.getOrderStatus())
			.orderItems(order.getOrderItems().stream().map(OrderItemDTO::from).toList())
			.build();
	}
}
