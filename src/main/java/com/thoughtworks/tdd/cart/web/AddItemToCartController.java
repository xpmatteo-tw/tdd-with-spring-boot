package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.AddItemToCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AddItemToCartController {
    private final AddItemToCartService service;

    public record AddItemToCartRequest(String productId, int quantity) {
    }

    public record AddItemToCartResponse(List<RequestCartItem> items) {
        public record RequestCartItem(String productId, int quantity) {}
    }

    @Autowired
    public AddItemToCartController(AddItemToCartService service) {
        this.service = service;
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Object> addItemToCart(@PathVariable String cartId, @RequestBody Object request) {
        return ResponseEntity.ok(new AddItemToCartResponse(List.of(new AddItemToCartResponse.RequestCartItem("P456", 2))));
    }
}
