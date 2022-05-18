package com.example.splitwise.service;

import com.example.splitwise.models.Amount;
import com.example.splitwise.models.BalanceMap;
import com.example.splitwise.models.Currency;
import com.example.splitwise.models.Expense;
import com.example.splitwise.models.PaymentGraph;

import java.util.*;

public class UserBalanceService {

    private final PaymentGraph userPaymentGraph;

    public UserBalanceService() {
        this.userPaymentGraph = new PaymentGraph();
    }

    void balancePaymentGraph(Expense expense) {
        BalanceMap balanceMap = expense.getUserBalances();

        final Comparator<Map.Entry<String, Amount>> ascending = Comparator.comparingDouble(balance -> balance.getValue().getAmount());
        final PriorityQueue<Map.Entry<String, Amount>>
                positiveAmounts = new PriorityQueue<>(ascending.reversed()),
                negativeAmounts = new PriorityQueue<>(ascending);
        for (var balance : balanceMap.getBalances().entrySet()) {
            if (balance.getValue().getAmount() > 0) {
                positiveAmounts.add(balance);
            } else {
                negativeAmounts.add(balance);
            }
        }
        while (!positiveAmounts.isEmpty()) {
            //eliminate largest from both groups
            final var largestPositive = positiveAmounts.poll();
            final var largestNegative = negativeAmounts.poll();
            final var negativeAmount = -largestNegative.getValue().getAmount();
            final var positiveAmount = largestPositive.getValue().getAmount();

            userPaymentGraph.getGraph().putIfAbsent(largestPositive.getKey(), new BalanceMap());

            Amount positiveKeyamount = userPaymentGraph.getGraph().get(largestPositive.getKey()).getBalances()
                    .getOrDefault(largestNegative.getKey(), new Amount(Currency.USD, 0.0));

            userPaymentGraph.getGraph().get(largestPositive.getKey()).getBalances()
                    .put(largestNegative.getKey(), positiveKeyamount.add(new Amount(Currency.USD,
                            Math.min(positiveAmount, negativeAmount))));

            userPaymentGraph.getGraph().putIfAbsent(largestNegative.getKey(), new BalanceMap());

            Amount negativeKeyAmount = userPaymentGraph.getGraph().get(largestNegative.getKey()).getBalances()
                    .getOrDefault(largestPositive.getKey(), new Amount(Currency.USD, 0.0));

            userPaymentGraph.getGraph().get(largestNegative.getKey()).getBalances().put(largestPositive.getKey(),
                    negativeKeyAmount.add(new Amount(Currency.USD, -1 * Math.min(positiveAmount, negativeAmount))));
            double remaining = positiveAmount - negativeAmount;
            final var remainingAmount = new Amount(Currency.USD, remaining);
            if (remaining > 0) {
                positiveAmounts.add(new AbstractMap.SimpleEntry<>(largestPositive.getKey(), remainingAmount));
            } else if (remaining < 0) {
                negativeAmounts.add(new AbstractMap.SimpleEntry<>(largestNegative.getKey(), remainingAmount));
            }
        }

    }

    BalanceMap getPaymentGraph(String userID) {
        return userPaymentGraph.getGraph().get(userID);
    }

    void removeFromBalances(Expense expense) {
        BalanceMap balanceMap = expense.getUserBalances();
        for (Map.Entry<String, Amount> amountEntry : balanceMap.getBalances().entrySet()) {
            balanceMap.getBalances().put(amountEntry.getKey(),
                    amountEntry.getValue().multiply(new Amount(Currency.USD, -1.0)));
        }
        balancePaymentGraph(expense);
    }

}
