package com.thoughtworks.tdd.cart.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findCart(CartId cartId);

    void save(Cart cart);
}
