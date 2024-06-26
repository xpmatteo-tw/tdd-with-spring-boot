package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import com.thoughtworks.tdd.cart.service.AddItemToCartService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemToCartControllerTest {

    // given   cart with id C123 exists
    //and     the cart contains 2xP222
    //when    I add 3 x product P333 to cart C123
    //then    the cart now contains 2xP222, 3xP333
    //and     the cart is returned

    @Test
    void addsItemToCart() {
        var service = mock(AddItemToCartService.class);
        var controller = new AddItemToCartController(service);
        when(service.addItemToCart(CartId.of("C123"), Quantity.of(3), ProductId.of("P333")))
                .thenReturn(new Cart()
                        .add(Quantity.of(2), ProductId.of("P222"))
                );

        ResponseEntity<AddItemToCartController.Response> result =
                controller.addItemToCart("C123", new AddItemToCartController.Request(3, "P333"));

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(
                new AddItemToCartController.Response(
                        List.of(AddItemToCartController.Pair.of(2, "P222"),
                                AddItemToCartController.Pair.of(3, "P333"))
                ));
    }
}
