package com.thoughtworks.tdd.cart.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AddItemToCartController {

    // This is a fake implementation of the controller that we use to verify
    // that the first acceptance test can pass
    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Object> addItemToCart(@PathVariable String cartId, @RequestBody Object request) {
        Object body = Map.of("items",
                List.of(Map.of("productId", "P456", "quantity", 1)));
        return ResponseEntity.ok(body);
    }
}
