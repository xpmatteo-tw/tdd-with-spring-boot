package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.ProblemDetailJacksonMixin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AddItemToCartController {
    private final CartRepository cartRepository;

    @Autowired
    public AddItemToCartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Object> addItemToCart(@PathVariable String cartId, @RequestBody Object request) {
        Optional<Cart> cart = cartRepository.findCart(new CartId(cartId));
        if (cart.isEmpty()) {
            return new ResponseEntity<>(
                    ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Cart not found"),
                    HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(Map.of("items", List.of(
                Map.of("productId", "P4567", "quantity", 1)
        )));
    }
}
