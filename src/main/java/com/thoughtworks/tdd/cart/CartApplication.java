package com.thoughtworks.tdd.cart;

import com.thoughtworks.tdd.cart.db.PostgresCartRepository;
import com.thoughtworks.tdd.cart.domain.CartRepository;
import com.thoughtworks.tdd.cart.service.AddItemToCartService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Bean
	public AddItemToCartService addItemToCartService(CartRepository cartRepository) {
		return new AddItemToCartService(cartRepository);
	}

	@Bean
	public CartRepository cartRepository(JdbcTemplate jdbcTemplate) {
		return new PostgresCartRepository(jdbcTemplate);
	}
}
