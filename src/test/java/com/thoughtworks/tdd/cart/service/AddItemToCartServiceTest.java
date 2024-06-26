package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemToCartServiceTest {

    @Test
    void addsItemToCart() {
        var repository = mock(CartRepository.class);
        var service = new AddItemToCartService();
        when(repository.findCart(CartId.of("C123")))
                .thenReturn(Optional.of(new Cart().add(Quantity.of(2), ProductId.of("P222"))));

        var cart = service.addItemToCart(CartId.of("C123"), Quantity.of(3), ProductId.of("P333"));

        assertThat(cart.items()).containsExactly(
                new Cart.Item(Quantity.of(2), ProductId.of("P222")),
                new Cart.Item(Quantity.of(3), ProductId.of("P333"))
        );
    }
}
