package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddItemToCartService {
    private final CartRepository repository;

    public AddItemToCartService(CartRepository repository) {
        this.repository = repository;
    }

    public Cart addItemToCart(CartId cartId, Quantity quantity, ProductId productId) {
        Optional<Cart> optionalCart = repository.findCart(cartId);
        if (optionalCart.isEmpty()) {
            throw new UnsupportedOperationException("Not implemented");
        }
        Cart cart = optionalCart.get();
        repository.save(cart);
        return cart.add(quantity, productId);
    }
}
