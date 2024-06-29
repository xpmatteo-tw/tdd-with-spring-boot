package com.thoughtworks.tdd.cart.db;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.CartRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeCartRepository implements CartRepository {
    private final Map<CartId, Cart> carts = new HashMap<>();

    @Override
    public Optional<Cart> findCart(CartId cartId) {
        return Optional.ofNullable(carts.get(cartId));
    }

    @Override
    public void save(Cart cart) {
        if (cart.cartId() == null) {
            throw new IllegalArgumentException("Cart has no id");
        }
        carts.put(cart.cartId(), cart);
    }
}
