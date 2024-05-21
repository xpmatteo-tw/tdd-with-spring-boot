package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.*;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartRequest;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartResponse;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartResponse.RequestCartItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemToCartControllerTest {

    AddItemToCartService addItemToCartService = mock(AddItemToCartService.class);
    AddItemToCartController controller = new AddItemToCartController(addItemToCartService);

    @Test
    void returns200OK() {
        var cartId = new CartId("C123");
        var productId = new ProductId("P456");
        var quantity = new Quantity(2);
        when(addItemToCartService.addItemToCart(cartId, productId, quantity))
                .thenReturn(Cart.with(productId, quantity));
        var request = new AddItemToCartRequest("P456", 2);

        var response = controller.addItemToCart("C123", request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        var expectedResponse = new AddItemToCartResponse(List.of(new RequestCartItem("P456", 2)));
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

}
