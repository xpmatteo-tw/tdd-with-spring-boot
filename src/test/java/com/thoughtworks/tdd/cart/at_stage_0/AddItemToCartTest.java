package com.thoughtworks.tdd.cart.at_stage_0;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.tdd.cart.CartApplication;
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

@Disabled("feature is WIP")
@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void add_one_item_to_empty_cart() throws JsonProcessingException {
        var request = """
                {
                    "productId": "P456",
                    "quantity": 1
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C123", toObject(request), String.class);

        var expectedResponse = """
                {
                    "items": [
                        {"productId": "P456", "quantity": 1}
                    ]
                }
                """;
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(toObject(responseEntity.getBody())).isEqualTo(toObject(expectedResponse));
    }

    public Object toObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Object.class);
    }
}
