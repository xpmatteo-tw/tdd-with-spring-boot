package com.thoughtworks.tdd.cart.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    public static Cart copyOf(Cart original) {
        Cart copy = new Cart();
        copy.items.addAll(original.items);
        return copy;
    }

    public record Item(Quantity quantity, ProductId productId) {
    }

    private final List<Item> items = new ArrayList<>();

    public Cart add(Quantity quantity, ProductId productId) {
        items.add(new Item(quantity, productId));
        return this;
    }

    public List<Item> items() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
