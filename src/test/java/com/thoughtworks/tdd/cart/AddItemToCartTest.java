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
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
class RepositoryConfiguration {
    public static CartRepository fakeCartRepository = mock(CartRepository.class);

    @Bean
    public CartRepository getCartRepository() {
        return fakeCartRepository;
    }
}

@Disabled("feature is WIP")
@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
@Import(RepositoryConfiguration.class)
public class AddItemToCartTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addOneItem_ok() throws JsonProcessingException {
        when(RepositoryConfiguration.fakeCartRepository.findCart(new CartId("C1234")))
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
        when(RepositoryConfiguration.fakeCartRepository.findCart(any()))
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
