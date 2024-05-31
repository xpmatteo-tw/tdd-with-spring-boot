package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;

import java.util.Map;
import java.util.Optional;

public class AddItemToCartService implements AddsItemToCart {
    private final CartRepository repository;

    public AddItemToCartService(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Map<ProductId, Quantity> addItemToCart(CartId cartId, ProductId productId, Quantity quantity) {
        Optional<Cart> optionalCart = repository.findCart(cartId);
        Cart cart = optionalCart.get();
        cart.addItem(productId, quantity);
        repository.save(cart);
        return cart.items();
    }
}
