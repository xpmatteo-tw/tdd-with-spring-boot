package com.thoughtworks.tdd.cart.domain;

public interface AddItemToCartService {
    Cart addItemToCart(CartId cartId, ProductId productId, Quantity quantity);
}
