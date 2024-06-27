package com.thoughtworks.tdd.cart.service;

import com.thoughtworks.tdd.cart.domain.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddItemToCartServiceTest {
    CartRepository repository = mock(CartRepository.class);
    AddItemToCartService service = new AddItemToCartService(repository);

    @Test
    void returns_the_updated_cart() {
        when(repository.findCart(CartId.of("C123")))
                .thenReturn(Optional.of(new Cart().add(Quantity.of(2), ProductId.of("P222"))));

        var cart = service.addItemToCart(CartId.of("C123"), Quantity.of(3), ProductId.of("P333"));

        assertThat(cart.items()).containsExactly(
                new Cart.Item(Quantity.of(2), ProductId.of("P222")),
                new Cart.Item(Quantity.of(3), ProductId.of("P333"))
        );
    }

    @Test
    void saves_the_updated_cart_to_the_repository() {
        when(repository.findCart(CartId.of("C123")))
                .thenReturn(Optional.of(new Cart().add(Quantity.of(2), ProductId.of("P222"))));

        service.addItemToCart(CartId.of("C123"), Quantity.of(3), ProductId.of("P333"));

        var expectedCart = new Cart()
                .add(Quantity.of(2), ProductId.of("P222"))
                .add(Quantity.of(3), ProductId.of("P333"));
        verify(repository).save(refEq(expectedCart));
    }
}
