package com.thoughtworks.tdd.cart.at_stage_0;

import com.thoughtworks.tdd.cart.CartApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

//@Disabled("feature is WIP")
//@Tag("acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class AddItemToCartTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void add_one_item_to_empty_cart() {
        var request = """
                {
                    "productId": "P456",
                    "quantity": 1
                }
                """;

        var responseEntity = restTemplate.postForEntity("/carts/C123", request, String.class);

        var expectedResponse = """
                {
                    "items": [
                        {"productId": "P456", "quantity": 1}
                    ]
                }
                """;
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody()).isEqualTo(expectedResponse);
    }
}
