package programmers.coffee.order;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import programmers.coffee.order.domain.Order;
import programmers.coffee.order.domain.OrderItem;
import programmers.coffee.order.dto.OrderRequestDTO;
import programmers.coffee.order.repository.OrderItemRepository;
import programmers.coffee.order.repository.OrderRepository;
import programmers.coffee.order.service.OrderService;
import programmers.coffee.product.domain.Product;
import programmers.coffee.product.dto.NewProductDTO;
import programmers.coffee.product.repository.ProductRepository;

@SpringBootTest
public class OrderTest {

	@Autowired
	OrderService orderService;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	UUID orderId;
	Product product;

	@BeforeEach
	public void beforeEach() {
		NewProductDTO productDTO = new NewProductDTO();
		productDTO.setProductName("Test Coffee Bean");
		productDTO.setCategory("Coffee Bean");
		productDTO.setDescription("This is Test Coffee Bean");
		productDTO.setPrice(10000L);
		product = Product.from(productDTO);

		productRepository.save(product);

		OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
		orderRequestDTO.setEmail("hgh1472@naver.com");
		orderRequestDTO.setPostCode("11111");
		orderRequestDTO.setAddress("Seoul");
		HashMap<UUID, Integer> orderItemDto = new HashMap<>();
		orderItemDto.put(product.getProductId(), 5);
		orderRequestDTO.setOrderItems(orderItemDto);

		orderId = orderService.order(orderRequestDTO).getOrderId();
	}

	@Test
	@Transactional
	public void 주문_조회() {
		Order findOrder = orderRepository.findByEmail("hgh1472@naver.com",
			Sort.by(Sort.Direction.ASC, "createdAt")).get(0);

		Order order = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException("저장되지 않음"));
		assertThat(findOrder).isEqualTo(order);

		OrderItem orderItem = order.getOrderItems().get(0);
		assertThat(orderItem.getProduct()).isEqualTo(product);
	}
}
