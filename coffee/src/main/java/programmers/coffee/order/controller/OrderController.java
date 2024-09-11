package programmers.coffee.order.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import programmers.coffee.order.dto.CreateOrderResponseDTO;
import programmers.coffee.order.dto.OrderDTO;
import programmers.coffee.order.dto.OrderRequestDTO;
import programmers.coffee.order.service.OrderService;

@RestController
@RequiredArgsConstructor
@Slf4j
/**
 * Log 기록 코드 반복 발생 => AOP 적용으로 개선해야 함.
 */
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<?> order(@RequestBody OrderRequestDTO requestDTO) {
		log.info("=== [OrderController.order] Start ===");

		log.info("=== [OrderService.order] Start ===");
		CreateOrderResponseDTO responseDTO = orderService.order(requestDTO);
		log.info("=== [OrderService.order] End ===");

		log.info("Status : {}", responseDTO.getStatus());
		log.info("=== [OrderController.order] End ===");
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/order/{email}")
	public ResponseEntity<?> getOrder(@PathVariable String email) {
		List<OrderDTO> orders = orderService.getOrders(email);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@DeleteMapping("/order/{orderId}")
	public ResponseEntity<?> cancel(@PathVariable UUID orderId) {
		orderService.cancelOrder(orderId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/order")
	public ResponseEntity<?> getAllOrders() {
		List<OrderDTO> allOrders = orderService.getAllOrders();
		return new ResponseEntity<>(allOrders, HttpStatus.OK);
	}
}
