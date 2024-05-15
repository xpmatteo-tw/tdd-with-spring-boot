package com.thoughtworks.tdd.demo.domain;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findCard(CartId cartId);
}
