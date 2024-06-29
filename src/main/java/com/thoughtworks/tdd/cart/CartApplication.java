package com.thoughtworks.tdd.cart;

import com.thoughtworks.tdd.cart.db.FakeCartRepository;
import com.thoughtworks.tdd.cart.domain.CartRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Bean
	public CartRepository cartRepository() {
		return new FakeCartRepository();
	}
}
