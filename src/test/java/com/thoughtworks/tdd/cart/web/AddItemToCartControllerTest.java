package com.thoughtworks.tdd.cart.web;

import com.thoughtworks.tdd.cart.domain.AddsItemToCart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartRequest;
import com.thoughtworks.tdd.cart.web.AddItemToCartController.AddItemToCartResponse;
import org.junit.jupiter.api.Test;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AddItemToCartControllerTest {

    AddsItemToCart addsItemToCart = mock(AddsItemToCart.class, withSettings().strictness(Strictness.STRICT_STUBS));
    AddItemToCartController controller = new AddItemToCartController(addsItemToCart);

    @Test
    void returns200OK() throws AddsItemToCart.CartNotFound {
        var request = new AddItemToCartRequest("P456", 2);
        when(addsItemToCart.addItemToCart(new CartId("C123"), new ProductId("P456"), new Quantity(2)))
                .thenReturn(Map.of(new ProductId("P456"), new Quantity(3)));

        var response = controller.addItemToCart("C123", request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(
                new AddItemToCartResponse(
                        List.of(new Pair<>("P456", 3))));
    }

    @Test
    void cartNotFound() throws AddsItemToCart.CartNotFound {
        var request = new AddItemToCartRequest("P456", 2);
        when(addsItemToCart.addItemToCart(new CartId("C123"), new ProductId("P456"), new Quantity(2)))
                .thenThrow(new AddsItemToCart.CartNotFound());

        var response = controller.addItemToCart("C123", request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNotNull();
        ProblemDetail expectedResponse = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Cart not found");
        assertThat(response.getBody()).isEqualTo(expectedResponse);
    }
}
