package com.thoughtworks.tdd.cart.acceptance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.tdd.cart.CartApplication;
import com.thoughtworks.tdd.cart.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartAcceptanceTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearDatabase() {
        jdbcTemplate.execute("delete from cart_items where cart_id = 'C123'");
        jdbcTemplate.execute("delete from carts where cart_id = 'C123'");
    }

    @Test
    void add_one_item_to_nonempty_cart() throws JsonProcessingException {
        cartRepository.save(new Cart(CartId.of("C123"))
                .add(Quantity.of(2), ProductId.of("P222")));
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
        assertThat(responseEntity.getBody()).isEqualTo(toObject(expectedResponse));
        Optional<Cart> optionalCart = cartRepository.findCart(CartId.of("C123"));
        assertThat(optionalCart).isPresent();
        assertThat(optionalCart.get()).usingRecursiveComparison().isEqualTo(
                new Cart(CartId.of("C123"))
                        .add(Quantity.of(2), ProductId.of("P222"))
                        .add(Quantity.of(3), ProductId.of("P333")));
    }

//    @Disabled("feature is WIP")
    @Test
    void consolidate_items() throws JsonProcessingException {
        cartRepository.save(new Cart(CartId.of("C123"))
                .add(Quantity.of(2), ProductId.of("P456")));
        String request = """
                {
                    "productId": "P456",
                    "quantity": 3
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C123", toObject(request), Object.class);

        var expectedResponse = """
                {
                    "items": [
                        {"productId": "P456", "quantity": 5}
                    ]
                }
                """;
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(toObject(expectedResponse));
        Optional<Cart> optionalCart = cartRepository.findCart(CartId.of("C123"));
        assertThat(optionalCart).isPresent();
        assertThat(optionalCart.get()).usingRecursiveComparison().isEqualTo(
                new Cart(CartId.of("C123"))
                        .add(Quantity.of(5), ProductId.of("P456"))
        );
    }

    private Object toObject(String request) throws JsonProcessingException {
        return objectMapper.readValue(request, Object.class);
    }
}
