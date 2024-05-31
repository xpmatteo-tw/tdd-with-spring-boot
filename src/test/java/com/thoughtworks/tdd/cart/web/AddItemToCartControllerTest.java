package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.*;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartRequest;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartResponse;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartResponse.RequestCartItem;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.List;
import java.util.Optional;

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
        var addedQuantity = new Quantity(2);
        var updatedQuantity = new Quantity(3);
        when(addItemToCartService.addItemToCart(cartId, productId, addedQuantity))
                .thenReturn(Optional.of(Cart.with(productId, updatedQuantity)));
        var request = new AddItemToCartRequest("P456", 2);

        var response = controller.addItemToCart("C123", request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        var expectedResponse = new AddItemToCartResponse(List.of(new RequestCartItem("P456", 3)));
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    void addItemToCartWithDifferentProduct() {
        var cartId = new CartId("C123");
        var product1 = new ProductId("P111");
        var product2 = new ProductId("P222");
        var quantity = new Quantity(1);
        when(addItemToCartService.addItemToCart(cartId, product1, quantity))
                .thenReturn(Optional.of(Cart.with(product1, quantity, product2, quantity)));
        var request = new AddItemToCartRequest("P111", 1);

        var response = controller.addItemToCart("C123", request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        var expectedResponse = new AddItemToCartResponse(List.of(
                new RequestCartItem("P111", 1),
                new RequestCartItem("P222", 1)
        ));
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }

    @Test
    void cartNotFound() {
        var cartId = new CartId("C123");
        var productId = new ProductId("P456");
        var addedQuantity = new Quantity(2);
        var request = new AddItemToCartRequest("P456", 1);
        when(addItemToCartService.addItemToCart(cartId, productId, addedQuantity))
                .thenReturn(Optional.empty());

        var response = controller.addItemToCart("C123", request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        ProblemDetail expectedResponse = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Cart not found");
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}
