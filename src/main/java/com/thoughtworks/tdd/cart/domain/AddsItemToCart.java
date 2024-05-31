package com.thoughtworks.tdd.cart.domain;

import java.util.Map;

public interface AddsItemToCart {
    Map<ProductId, Quantity> addItemToCart(CartId cartId, ProductId productId, Quantity quantity)
            throws CartNotFound;

    class CartNotFound extends Exception {
    }
}
