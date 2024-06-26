package com.thoughtworks.tdd.cart;

import com.thoughtworks.tdd.cart.service.AddItemToCartService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CartApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartApplication.class, args);
	}

	@Bean
	public AddItemToCartService addItemToCartService() {
		return new AddItemToCartService();
	}

}
