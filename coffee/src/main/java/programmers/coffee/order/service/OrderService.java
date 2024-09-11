package programmers.coffee.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programmers.coffee.order.domain.Order;
import programmers.coffee.order.domain.OrderItem;
import programmers.coffee.order.dto.CreateOrderResponseDTO;
import programmers.coffee.order.dto.OrderDTO;
import programmers.coffee.order.dto.OrderRequestDTO;
import programmers.coffee.order.repository.OrderItemRepository;
import programmers.coffee.order.repository.OrderRepository;
import programmers.coffee.product.domain.Product;
import programmers.coffee.product.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final ProductRepository productRepository;

	/**
	 * 여러 개의 ENTITY 저장 시 Bulk Insert로 처리, but INSERT 쿼리가 N번 나가는 문제 발생
	 * 이유 : PK 전략이 IDENTITY인 경우 Bulk Insert로 처리하지 않는다. Insert를 실행하기 전까지 ID값을 모르기 때문
	 * 해결 : QueryDSL을 사용하거나 Mybatis 또는 JDBCTemplate을 사용하여 해결
	 */

	public CreateOrderResponseDTO order(OrderRequestDTO orderRequestDTO) {
		Order order = Order.from(orderRequestDTO);
		Map<UUID, Integer> orderItemDTOs = orderRequestDTO.getOrderItems();
		Set<UUID> productIds = orderItemDTOs.keySet();

		List<Product> products = productRepository.findAllById(productIds);

		List<OrderItem> orderItems = new ArrayList<>();
		for (Product product : products) {
			Integer quantity = orderItemDTOs.get(product.getProductId());
			OrderItem orderItem = OrderItem.of(product, quantity, order);
			orderItems.add(orderItem);
			log.info("OrderItem : {}", orderItem);
		}

		order.registerOrderItem(orderItems);
		orderRepository.save(order);

		return CreateOrderResponseDTO.from(order);
	}

	public List<OrderDTO> getOrders(String email) {
		List<Order> byEmail = orderRepository.findByEmail(email, Sort.by(Sort.Direction.ASC, "createdAt"));
		log.info("=== byEmail ===");
		 return byEmail.stream()
			.map(OrderDTO::from)
			.toList();
	}

	public void cancelOrder(UUID orderId) {
		orderRepository.deleteById(orderId);
	}

	public List<OrderDTO> getAllOrders() {
		return orderRepository.findAll().stream()
			.map(OrderDTO::from)
			.toList();
	}
}
