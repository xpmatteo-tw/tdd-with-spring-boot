package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;

import java.util.Optional;

public class AddItemToCartService implements AddsItemToCart {
    @Override
    public Optional<Cart> addItemToCart(CartId cartId, ProductId productId, Quantity quantity) {
        return Optional.empty();
    }
}
