package com.example.splitwise.models;

public class Amount {
    private final Currency currency;
    private final double value;

    public Amount(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public Amount add(Amount amount) {
        return new Amount(currency, this.value + amount.value);
    }

    public Amount multiply(Amount amount) {
        return new Amount(currency, this.value * amount.value);
    }

    public double getAmount() {
        return value;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "currency=" + currency +
                ", amount=" + value +
                '}';
    }

}
