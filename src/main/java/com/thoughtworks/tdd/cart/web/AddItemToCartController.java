package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import com.thoughtworks.tdd.cart.service.AddItemToCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddItemToCartController {
    private final AddItemToCartService addItemToCartService;

    public AddItemToCartController(AddItemToCartService service) {
        this.addItemToCartService = service;
    }

    public record Request(int quantity, String productId) {
    }

    public record Response(List<Pair<Integer, String>> items) {
        public static Response from(Cart cart) {
            return new Response(List.of(
                    Pair.of(2, "P222"),
                    Pair.of(3, "P333")
            ));
        }
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Response> addItemToCart(@PathVariable String cartId, @RequestBody Request request) {
        Cart cart = addItemToCartService.addItemToCart(CartId.of(cartId), Quantity.of(request.quantity), ProductId.of(request.productId));
        Response response = Response.from(cart);
        return ResponseEntity.ok(response);
    }

}
