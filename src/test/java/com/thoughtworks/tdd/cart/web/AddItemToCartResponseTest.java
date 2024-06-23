package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

class AddItemToCartResponseTest {

    @Test
    void fromEmptyCart() {
        assertThat(Response.from(new Cart())).isEqualTo(new Response(emptyList()));
    }

    @Test
    void fromSingleItemCart() {
        Cart cart = new Cart().add(Quantity.of(2), ProductId.of("P222"));

        Response actual = Response.from(cart);

        Response expected = new Response(List.of(Pair.of(2, "P222")));
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void fromCartWithTwoItems() {
        Cart cart = new Cart()
                .add(Quantity.of(2), ProductId.of("P222"))
                .add(Quantity.of(1), ProductId.of("P111"));

        Response actual = Response.from(cart);

        Response expected = new Response(List.of(
                Pair.of(2, "P222"),
                Pair.of(1, "P111")
        ));
        assertThat(actual).isEqualTo(expected);
    }
}
