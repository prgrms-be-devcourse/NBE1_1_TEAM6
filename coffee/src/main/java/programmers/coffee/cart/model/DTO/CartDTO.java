package programmers.coffee.cart.model.DTO;

import lombok.Data;

@Data
public class CartDTO {
    private long productId;
    private String productName;
    private int price;
    private int quantity;
    private int totalPrice;
}
