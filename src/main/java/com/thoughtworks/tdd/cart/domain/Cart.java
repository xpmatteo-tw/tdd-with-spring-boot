package com.thoughtworks.tdd.cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    public record Item(Quantity quantity, ProductId productId) {
    }

    public static Cart copyOf(Cart original) {
        Cart copy = new Cart(original.cartId);
        copy.items.addAll(original.items);
        return copy;
    }

    private final CartId cartId;
    private final List<Item> items = new ArrayList<>();

    public Cart() {
        this(null);
    }

    public Cart(CartId cartId) {
        this.cartId = cartId;
    }

    public Cart add(Quantity quantity, ProductId productId) {
        items.add(new Item(quantity, productId));
        return this;
    }

    public List<Item> items() {
        return Collections.unmodifiableList(items);
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
