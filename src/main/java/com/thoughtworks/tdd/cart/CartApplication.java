package com.thoughtworks.tdd.cart;

import com.thoughtworks.tdd.cart.db.FakeCartRepository;
import com.thoughtworks.tdd.cart.domain.*;
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
	public AddItemToCartService addItemToCartService(CartRepository cartRepository) {
		return new AddItemToCartService(cartRepository);
	}

	@Bean
	public CartRepository cartRepository() {
		FakeCartRepository repository = new FakeCartRepository();
		// test data
		repository.save(new Cart(CartId.of("C000")));
		repository.save(new Cart(CartId.of("C001")).add(Quantity.of(1), ProductId.of("P111")));
		return repository;
	}
}
