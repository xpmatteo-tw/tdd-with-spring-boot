package com.thoughtworks.tdd.cart.domain;

public record Quantity(int value) {
    public static Quantity of(int value) {
        return new Quantity(value);
    }
}
