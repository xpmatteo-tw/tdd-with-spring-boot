package com.thoughtworks.tdd.cart.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findCard(CartId cartId);
}
