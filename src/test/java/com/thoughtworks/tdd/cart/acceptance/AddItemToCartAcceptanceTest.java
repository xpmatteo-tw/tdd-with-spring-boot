package com.thoughtworks.tdd.cart.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.tdd.cart.CartApplication;
import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.CartRepository;
import com.thoughtworks.tdd.cart.domain.ProductId;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
class RepositoryConfiguration {
    public static CartRepository fakeCartRepository = mock(CartRepository.class);

    @Bean
    public CartRepository cartRepository() {
        return fakeCartRepository;
    }
}

@Disabled("feature is WIP")
@Import(RepositoryConfiguration.class)
@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartAcceptanceTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void add_one_item_to_empty_cart() throws JsonProcessingException {
        when(RepositoryConfiguration.fakeCartRepository.findCart(new CartId("C123")))
                .thenReturn(Optional.of(new Cart()));
        String request = """
                {
                    "productId": "P456",
                    "quantity": 1
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C123", toObject(request), Object.class);

        var expectedResponse = """
                {
                    "items": [
                        {"productId": "P456", "quantity": 1}
                    ]
                }
                """;
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).describedAs("body is null").isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(toObject(expectedResponse));
    }

    @Test
    void add_one_item_to_nonempty_cart() throws JsonProcessingException {
        when(RepositoryConfiguration.fakeCartRepository.findCart(new CartId("C123")))
                .thenReturn(Optional.of(
                        new Cart().add(2, ProductId.of("P222"))));
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
