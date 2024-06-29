package com.thoughtworks.tdd.cart.acceptance;

import com.thoughtworks.tdd.cart.CartApplication;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("Acceptance")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CartApplication.class)
public class SpringContextTest {
        @Test
        void contextLoads() {
        }

}
