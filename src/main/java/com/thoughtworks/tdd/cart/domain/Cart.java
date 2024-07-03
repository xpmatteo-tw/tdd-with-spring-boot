package com.thoughtworks.tdd.cart.domain;

import java.util.*;

public class Cart {
    public record Item(Quantity quantity, ProductId productId) {
    }

    public static Cart copyOf(Cart original) {
        Cart copy = new Cart(original.cartId);
        copy.items.putAll(original.items);
        return copy;
    }

    private final CartId cartId;
    private final Map<ProductId, Quantity> items = new LinkedHashMap<>();

    public Cart() {
        this(null);
    }

    public Cart(CartId cartId) {
        this.cartId = cartId;
    }

    public Cart add(Quantity newQuantity, ProductId productId) {
        Quantity oldQuantity = items.get(productId);
        if (oldQuantity == null) {
            items.put(productId, newQuantity);
        } else {
            items.put(productId, oldQuantity.add(newQuantity));
        }
        return this;
    }

    public List<Item> items() {
        return items.entrySet().stream()
                .map(entry -> new Item(entry.getValue(), entry.getKey()))
                .toList();
    }

    public CartId cartId() {
        return cartId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", items=" + items +
                '}';
    }
}
