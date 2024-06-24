package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import com.thoughtworks.tdd.cart.service.AddItemToCartService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemToCartControllerTest {

    @Test
    void addsItemToCart() {
        var service = mock(AddItemToCartService.class);
        var controller = new AddItemToCartController(service);
        when(service.addItemToCart(CartId.of("C123"), Quantity.of(3), ProductId.of("P333")))
                .thenReturn(new Cart()
                        .add(Quantity.of(2), ProductId.of("P222"))
                        .add(Quantity.of(3), ProductId.of("P333"))
                );

        var response = controller.addItemToCart("C123", new AddItemToCartController.Request(3, "P333"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(new AddItemToCartController.Response(List.of(
                new AddItemToCartController.Pair(2, "P222"),
                new AddItemToCartController.Pair(3, "P333")
        )));
    }
}
