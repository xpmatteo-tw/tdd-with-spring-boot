package com.thoughtworks.tdd.cart.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class Cart {
    private final Map<ProductId, Quantity> items = new HashMap<>();

    public static Cart with(ProductId p1, Quantity q1, ProductId p2, Quantity q2) {
        Cart cart = new Cart();
        cart.items.put(p1, q1);
        cart.items.put(p2, q2);
        return cart;
    }

    public static Cart with(ProductId productId, Quantity quantity) {
        Cart cart = new Cart();
        cart.items.put(productId, quantity);
        return cart;
    }

    public void forAllProducts(BiConsumer<ProductId, Quantity> f) {
        this.items.forEach(f);
    }

}
