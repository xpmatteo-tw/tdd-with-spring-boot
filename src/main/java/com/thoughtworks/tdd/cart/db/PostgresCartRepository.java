package com.thoughtworks.tdd.cart.db;

import com.thoughtworks.tdd.cart.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.Types;
import java.util.Optional;

public class PostgresCartRepository implements CartRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostgresCartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Cart> findCart(CartId cartId) {
        var sql = "select count(*) from carts where cart_id = ?";
        var count = jdbcTemplate.queryForObject(sql, new Object[]{cartId.value()}, new int[]{Types.VARCHAR}, Integer.class);
        if (count == 0) {
            return Optional.empty();
        }
        var sqlItems = "select quantity, product_id from cart_items where cart_id = ?";
        var sqlRowSet = jdbcTemplate.queryForRowSet(sqlItems, cartId.value());
        var result = new Cart(CartId.of(cartId.value()));
        while (sqlRowSet.next()) {
            var quantity = Quantity.of(sqlRowSet.getInt(1));
            var productId = ProductId.of(sqlRowSet.getString(2));
            result.add(quantity, productId);
        }
        return Optional.of(result);
    }

    @Override
    public void save(Cart cart) {
        String sqlCart = """
        INSERT INTO carts (cart_id)
        VALUES (?)
        ON CONFLICT (cart_id) DO NOTHING
        """;
        jdbcTemplate.update(sqlCart, cart.cartId().value());

        for (var item : cart.items()) {
            String sqlCartItem = """
                    INSERT INTO cart_items (cart_id, quantity, product_id) 
                    VALUES (?,?,?)
                    ON CONFLICT DO NOTHING
                    """;
            jdbcTemplate.update(sqlCartItem,
                    cart.cartId().value(),
                    item.quantity().value(),
                    item.productId().value());
        }
    }
}
