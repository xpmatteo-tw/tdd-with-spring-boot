package com.thoughtworks.tdd.cart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addOneItem_ok() throws JsonProcessingException {
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

    public Object toObject(String json) throws JsonProcessingException {
        return objectMapper.readValue(json, Object.class);
    }
}
