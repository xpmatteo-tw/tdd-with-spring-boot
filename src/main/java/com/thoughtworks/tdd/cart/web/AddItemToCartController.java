package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AddItemToCartController {
    private final AddsItemToCart service;

    public record AddItemToCartRequest(String productId, int quantity) {
    }

    public record AddItemToCartResponse(List<RequestCartItem> items) {
        private static AddItemToCartResponse fromCart(Cart cart) {
            var cartItems = new ArrayList<RequestCartItem>();
            cart.forAllProducts((p, q) -> cartItems.add(new RequestCartItem(p.value(), q.value())));
            return new AddItemToCartResponse(cartItems);
        }

        public record RequestCartItem(String productId, int quantity) {}
    }

    @Autowired
    public AddItemToCartController(AddsItemToCart service) {
        this.service = service;
    }

    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Object> addItemToCart(@PathVariable String cartId, @RequestBody AddItemToCartRequest request) {
        var cart = this.service.addItemToCart(new CartId(cartId), new ProductId(request.productId), new Quantity(request.quantity));
        if (cart.isEmpty()) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Cart not found")).build();
        }
        return ResponseEntity.ok(AddItemToCartResponse.fromCart(cart.orElseThrow()));
    }

}
