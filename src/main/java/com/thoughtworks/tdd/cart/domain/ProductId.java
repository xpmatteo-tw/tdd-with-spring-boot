package com.thoughtworks.tdd.cart.domain;

public record ProductId(String value) {
    public static ProductId of(String value) {
        return new ProductId(value);
    }
}
