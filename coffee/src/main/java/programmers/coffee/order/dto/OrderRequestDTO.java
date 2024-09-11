package programmers.coffee.order.dto;

import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class OrderRequestDTO {

	private String email;
	private String address;
	private String postCode;
	private Map<UUID, Integer> orderItems;
}
