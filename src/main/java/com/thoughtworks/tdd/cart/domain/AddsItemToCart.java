package com.thoughtworks.tdd.cart.domain;

import java.util.Optional;

public interface AddsItemToCart {
    Optional<Cart> addItemToCart(CartId cartId, ProductId productId, Quantity quantity);
}
