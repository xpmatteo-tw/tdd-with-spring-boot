package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import org.springframework.stereotype.Service;

@Service
public class AddItemToCartService {
    public Cart addItemToCart(CartId cartId, Quantity quantity, ProductId productId) {
        return null;
    }
}
