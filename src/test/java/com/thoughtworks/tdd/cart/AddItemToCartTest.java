package com.thoughtworks.tdd.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.CartRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Disabled("feature is WIP")
@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CartRepository cartRepository;

    @Test
    void addOneItem_ok() throws JsonProcessingException {
        when(cartRepository.findCart(new CartId("C1234")))
                .thenReturn(Optional.of(new Cart()));

        var request = """
                {
                    "productId": "P4576",
                    "quantity": 1
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C1234", toObject(request), String.class);

        var expectedResponse = """
                {
                    "items": [
                        {"productId": "P4567", "quantity": 1}
                    ]
                }
                """;
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(toObject(responseEntity.getBody())).isEqualTo(toObject(expectedResponse));
    }

    @Test
    void addOneItem_cartNotFound() throws JsonProcessingException {
        when(cartRepository.findCart(any()))
                .thenReturn(Optional.empty());
        var request = """
                {
                    "productId": "P4576",
                    "quantity": 1
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C9999", toObject(request), ProblemDetail.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getDetail()).isEqualTo("Cart not found");
    }

    public Object toObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Object.class);
    }
}
