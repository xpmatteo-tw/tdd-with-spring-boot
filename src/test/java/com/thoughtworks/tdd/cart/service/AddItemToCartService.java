package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.AddsItemToCart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;

import java.util.HashMap;
import java.util.Map;

public class AddItemToCartService implements AddsItemToCart {
    @Override
    public Map<ProductId, Quantity> addItemToCart(CartId cartId, ProductId productId, Quantity quantity) {

        return new HashMap<>();
    }
}
