package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;
import org.junit.jupiter.api.Test;

import java.util.AbstractMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AddItemToCartServiceTest {

    CartRepository repository = mock(CartRepository.class);
    AddItemToCartService service = new AddItemToCartService(repository);

    @Test
    void cartFound() throws AddsItemToCart.CartNotFound {
        Cart cart = new Cart();
        Quantity quantity = new Quantity(2);
        when(repository.findCart(new CartId("C123"))).thenReturn(Optional.of(cart));

        var map = service.addItemToCart(new CartId("C123"), new ProductId("P111"), new Quantity(2));

        assertThat(map).containsExactly(
                new AbstractMap.SimpleEntry<>(new ProductId("P111"), quantity)
        );
        verify(repository).save(cart);
    }

    @Test
    void cartNotFound() {
        when(repository.findCart(new CartId("C123"))).thenReturn(Optional.empty());

        assertThatThrownBy( () -> service.addItemToCart(new CartId("C123"), anyProductId(), anyQuantity()))
                .isInstanceOf(AddsItemToCart.CartNotFound.class);
    }

    private static Quantity anyQuantity() {
        return new Quantity(2);
    }

    private static ProductId anyProductId() {
        return new ProductId("P111");
    }
}
