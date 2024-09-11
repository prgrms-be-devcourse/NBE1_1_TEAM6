package programmers.coffee.cart.controller;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import programmers.coffee.cart.model.DTO.CartDTO;
import programmers.coffee.cart.model.DTO.CartList;

@RestController
@RequestMapping("/cart")
public class CartController {

    @PostMapping
    public CartList addCart(@RequestBody CartDTO cart, HttpSession session){
//        System.out.println(cart);

        CartList cartList=(CartList) session.getAttribute("cartList");

        int totalPrice=cart.getPrice() * cart.getQuantity();

        if(cartList==null){
            List<CartDTO> newCart=new ArrayList<>();
            cart.setTotalPrice(totalPrice);
            newCart.add(cart);
            cartList=new CartList(newCart, totalPrice);
        }else{
            List<CartDTO> prevCart=cartList.getCart();
            int prevCartTotal=cartList.getCartTotal();
            cartList.setCartTotal(prevCartTotal+totalPrice);

            if(prevCart.contains(cart)){
                int cartIndex=prevCart.indexOf(cart);
                int quantity=cart.getQuantity();

                CartDTO newCart=prevCart.get(cartIndex);
                int newQuantity=newCart.getQuantity()+quantity;
                int newTotal=newCart.getTotalPrice()+totalPrice;

                newCart.setQuantity(newQuantity);
                newCart.setTotalPrice(newTotal);
                prevCart.set(cartIndex,newCart);
            }else{
                cart.setTotalPrice(totalPrice);
                prevCart.add(cart);
            }
        }

        session.setAttribute("cartList",cartList);

        return cartList;
    }
}
