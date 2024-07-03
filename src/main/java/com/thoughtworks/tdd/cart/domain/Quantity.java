package com.thoughtworks.tdd.cart.domain;

public record Quantity(int value) {
    public static Quantity of(int value) {
        return new Quantity(value);
    }

    public Quantity add(Quantity other) {
        return Quantity.of(this.value + other.value);
    }
}
