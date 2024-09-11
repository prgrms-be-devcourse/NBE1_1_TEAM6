package programmers.coffee.cart.model.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartList {
    List<CartDTO> cart;
    int cartTotal;
}
