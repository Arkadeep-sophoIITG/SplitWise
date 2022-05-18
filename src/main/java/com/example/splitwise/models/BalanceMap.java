package com.example.splitwise.models;

import java.util.HashMap;
import java.util.Map;

public class BalanceMap {

    private final Map<String, Amount> balanceMap;

    public BalanceMap(){
        this.balanceMap = new HashMap<>();
    }

    public BalanceMap(Map<String, Amount> balanceMap) {
        this.balanceMap = balanceMap;
    }

    public Map<String, Amount> getBalances(){
        return balanceMap;
    }

    @Override
    public String toString() {
        return "BalanceMap{" +
                "balanceMap=" + balanceMap +
                '}';
    }
}
