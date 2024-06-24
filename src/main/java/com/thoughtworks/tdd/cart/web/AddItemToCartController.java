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

    private final AddItemToCartService service;

    public AddItemToCartController(AddItemToCartService service) {
        this.service = service;
    }

    public record Request(int quantity, String productId) {
    }

    public record Response(List<Pair> items) {
        public static Response from(Cart cart) {
            List<Pair> pairs = cart.items().stream()
                    .map(item -> new Pair(item.quantity().value(), item.productId().value()))
                    .toList();

            return new Response(pairs);
        }
    }

    public record Pair(int quantity, String productId) {
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Object> addItemToCart(@PathVariable String cartId, @RequestBody Request request) {
        Cart cart = service.addItemToCart(CartId.of(cartId), Quantity.of(request.quantity), ProductId.of(request.productId));
        return ResponseEntity.ok(Response.from(cart));
    }

}
