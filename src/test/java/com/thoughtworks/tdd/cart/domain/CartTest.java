package com.thoughtworks.tdd.cart.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {
    @Test
    void consolidatesItems() {
        Cart cart = new Cart().add(Quantity.of(1), ProductId.of("P111"));

        cart.add(Quantity.of(2), ProductId.of("P111"));

        assertThat(cart.items())
                .containsExactly(new Cart.Item(Quantity.of(3), ProductId.of("P111")));
    }
}
