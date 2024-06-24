package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddItemToCartResponseTest {

    @Test
    void fromEmptyCart() {
        var cart = new Cart();

        var response = AddItemToCartController.Response.from(cart);

        assertThat(response).isEqualTo(new AddItemToCartController.Response(List.of()));
    }

    @Test
    void fromCartWithOneItem() {
        var cart = new Cart()
                .add(Quantity.of(7), ProductId.of("P777"));

        var response = AddItemToCartController.Response.from(cart);

        assertThat(response).isEqualTo(new AddItemToCartController.Response(List.of(
                new AddItemToCartController.Pair(7, "P777")
        )));
    }

    @Test
    void fromCartWithTwoItems() {
        var cart = new Cart()
                .add(Quantity.of(7), ProductId.of("P777"))
                .add(Quantity.of(8), ProductId.of("P888"))
                ;

        var response = AddItemToCartController.Response.from(cart);

        assertThat(response).isEqualTo(new AddItemToCartController.Response(List.of(
                new AddItemToCartController.Pair(7, "P777"),
                new AddItemToCartController.Pair(8, "P888")
        )));
    }

}
