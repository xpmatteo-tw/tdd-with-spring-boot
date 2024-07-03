package com.thoughtworks.tdd.cart.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.tdd.cart.CartApplication;
import com.thoughtworks.tdd.cart.domain.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartAcceptanceTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CartRepository cartRepository;

    @Test
    void add_one_item_to_nonempty_cart() throws JsonProcessingException {
        when(cartRepository.findCart(new CartId("C123")))
                .thenReturn(Optional.of(
                        new Cart().add(Quantity.of(2), ProductId.of("P222"))));
        String request = """
                {
                    "productId": "P333",
                    "quantity": 3
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C123", toObject(request), Object.class);

        var expectedResponse = """
                {
                    "items": [
                        {"productId": "P222", "quantity": 2},
                        {"productId": "P333", "quantity": 3}
                    ]
                }
                """;
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).describedAs("body is null").isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(toObject(expectedResponse));
    }

    private Object toObject(String request) throws JsonProcessingException {
        return objectMapper.readValue(request, Object.class);
    }
}
