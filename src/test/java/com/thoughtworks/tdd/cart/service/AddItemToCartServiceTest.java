package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddItemToCartServiceTest {

    CartRepository repository = mock(CartRepository.class);
    AddItemToCartService service = new AddItemToCartService(repository);

    @Test
    void cartFound() {
        Cart cart = new Cart();
        Quantity quantity = new Quantity(2);
        when(repository.findCart(new CartId("C123"))).thenReturn(Optional.of(cart));

        var map = service.addItemToCart(new CartId("C123"), new ProductId("P111"), new Quantity(2));

        assertThat(map).containsExactly(
                new AbstractMap.SimpleEntry<>(new ProductId("P111"), quantity)
        );
        verify(repository).save(cart);
    }
}
