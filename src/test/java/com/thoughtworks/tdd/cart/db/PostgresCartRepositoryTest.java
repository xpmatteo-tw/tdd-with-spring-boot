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
import static org.junit.jupiter.api.Assertions.*;

class PostgresCartRepositoryTest {

    JdbcTemplate jdbcTemplate = new JdbcTemplate(testDataSource());
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
    void canConnectToDb() {
        assertThat(jdbcTemplate.queryForObject("select 2+3", Integer.class))
                .isEqualTo(5);
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

}
