package com.thoughtworks.tdd.cart.db;

import com.thoughtworks.tdd.cart.domain.Cart;
import com.thoughtworks.tdd.cart.domain.CartId;
import com.thoughtworks.tdd.cart.domain.ProductId;
import com.thoughtworks.tdd.cart.domain.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PostgresCartRepositoryTest {
    static DataSource dataSource = testDataSource();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    PostgresCartRepository repository = new PostgresCartRepository(jdbcTemplate);

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("delete from cart_items");
        jdbcTemplate.execute("delete from carts");
    }

    private static DataSource testDataSource() {
        var dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/cart_test");
        dataSourceBuilder.username("cart_test");
        dataSourceBuilder.password("cart_test");
        return dataSourceBuilder.build();
    }

    @Test
    void findsCartById() {
        jdbcTemplate.update("insert into carts (cart_id) values (?)", "C123");

        Optional<Cart> optionalCart = repository.findCart(CartId.of("C123"));

        assertThat(optionalCart).isPresent();
        assertThat(optionalCart.get()).usingRecursiveComparison()
                .isEqualTo(new Cart(CartId.of("C123")));
    }

    @Test
    void returnsEmptyForNonExistentCart() {
        Optional<Cart> optionalCart = repository.findCart(CartId.of("C999"));

        assertThat(optionalCart).isEmpty();
    }

    @Test
    void findsNonemptyCart() {
        jdbcTemplate.update("insert into carts (cart_id) values (?)", "C123");
        jdbcTemplate.update("insert into cart_items (cart_id, quantity, product_id) values (?,?,?)",
                "C123", 3, "P456");

        Optional<Cart> optionalCart = repository.findCart(CartId.of("C123"));

        assertThat(optionalCart).isPresent();
        Cart expected = new Cart(CartId.of("C123")).add(Quantity.of(3), ProductId.of("P456"));
        assertThat(optionalCart.get()).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void savesNewEmptyCart() {
        Cart newEmptyCart = new Cart(CartId.of("C888"));

        repository.save(newEmptyCart);

        Optional<Cart> found = repository.findCart(CartId.of("C888"));
        assertThat(found).isPresent();
        assertThat(found.get()).usingRecursiveComparison().isEqualTo(newEmptyCart);
    }

    @Test
    void savesExistingEmptyCart() {
        Cart existingEmptyCart = new Cart(CartId.of("C888"));
        repository.save(existingEmptyCart);

        repository.save(existingEmptyCart);

        Optional<Cart> found = repository.findCart(CartId.of("C888"));
        assertThat(found).isPresent();
        assertThat(found.get()).usingRecursiveComparison().isEqualTo(existingEmptyCart);
    }

    @Test
    void savesNewNonEmptyCart() {
        Cart newNonEmptyCart = new Cart(CartId.of("C000"))
                .add(Quantity.of(3), ProductId.of("P333"));

        repository.save(newNonEmptyCart);

        Optional<Cart> found = repository.findCart(CartId.of("C000"));
        assertThat(found).isPresent();
        assertThat(found.get()).usingRecursiveComparison().isEqualTo(newNonEmptyCart);
    }

    @Test
    void addItemsToExistingCart() {
        Cart cart = new Cart(CartId.of("C000"))
                .add(Quantity.of(3), ProductId.of("P333"));
        repository.save(cart);
        cart.add(Quantity.of(4), ProductId.of("P444"));

        repository.save(cart);

        Optional<Cart> found = repository.findCart(CartId.of("C000"));
        assertThat(found).isPresent();
        assertThat(found.get()).usingRecursiveComparison().isEqualTo(cart);
    }


}
