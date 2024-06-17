package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AddItemToCartController {

    public record AddItemToCartRequest(String productId, int quantity) {
    }

    public record AddItemToCartResponse(List<Pair<String, Integer>> items) {
        private static AddItemToCartResponse fromMap(Map<ProductId, Quantity> map) {
            var items = new ArrayList<Pair<String, Integer>>();
            map.forEach((key, value) ->
                    items.add(new Pair<>(key.value(), value.value()))
            );
            return new AddItemToCartResponse(items);
        }
    }

//    @PostMapping("/carts/{cartId}")
    public ResponseEntity<Object> addItemToCart(@PathVariable String cartId, @RequestBody AddItemToCartRequest request) {
        return ResponseEntity.ok().build();
    }

}
