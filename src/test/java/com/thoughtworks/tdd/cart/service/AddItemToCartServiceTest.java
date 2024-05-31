package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemToCartServiceTest {

    CartRepository repository = mock(CartRepository.class);
    AddItemToCartService service = new AddItemToCartService();

    @Test
    void cartFound() {
        CartId cartId = new CartId("C123");
        Cart cart = new Cart();
        ProductId product = new ProductId("P111");
        Quantity quantity = new Quantity(2);
        when(repository.findCart(cartId)).thenReturn(Optional.of(cart));

        service.addItemToCart(cartId, product, quantity);

        assertThat(cart.items()).containsExactly(
                new AbstractMap.SimpleEntry<>(product, quantity)
        );
    }
}
