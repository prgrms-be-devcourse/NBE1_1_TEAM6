package programmers.coffee.cart.controller;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
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
        CartList cartList=(CartList) session.getAttribute("cartList"); //세션에서 가져오기

        int totalPrice=cart.getPrice() * cart.getQuantity();

        if(cartList==null){ //세션에 값이 없으면 -> 새로 세션에 넣기
            List<CartDTO> newCart=new ArrayList<>();
            cart.setTotalPrice(totalPrice);
            newCart.add(cart);
            cartList=new CartList(newCart, totalPrice);
        }else{ //세션에 값이 있으면
            List<CartDTO> prevCart=cartList.getCart();
            int prevCartTotal=cartList.getCartTotal();
            cartList.setCartTotal(prevCartTotal+totalPrice);

            boolean chk=false;
            for(CartDTO cartTemp :prevCart){
                if(cartTemp.getProductId()==cart.getProductId()){
                    chk=true;
                    int cartIndex=prevCart.indexOf(cartTemp);
                    int quantity=cart.getQuantity();

                    CartDTO newCart=prevCart.get(cartIndex);
                    int newQuantity=newCart.getQuantity()+quantity;
                    int newTotal=newCart.getTotalPrice()+totalPrice;

                    newCart.setQuantity(newQuantity);
                    newCart.setTotalPrice(newTotal);
                    prevCart.set(cartIndex,newCart);
                }
            }
            if(!chk){
                cart.setTotalPrice(totalPrice);
                prevCart.add(cart);
            }
        }

        session.setAttribute("cartList",cartList);

        return cartList;
    }

    @GetMapping
    public CartList cartList(HttpSession session){
        return (CartList) session.getAttribute("cartList");
    }
}
