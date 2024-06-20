package com.thoughtworks.tdd.cart.domain;

public record CartId(String value) {
    public static CartId of(String value) {
        return new CartId(value);
    }
}
