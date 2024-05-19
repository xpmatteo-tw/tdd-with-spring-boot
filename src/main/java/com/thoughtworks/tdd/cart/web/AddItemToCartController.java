package com.thoughtworks.tdd.cart.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddItemToCartController {
    @PostMapping("/carts/C1234")
    public ResponseEntity<Object> addItemToCart(@RequestBody Object request) {
        return ResponseEntity.ok(Map.of());
    }
}
