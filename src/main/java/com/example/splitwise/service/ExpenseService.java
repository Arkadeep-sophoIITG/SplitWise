package com.example.splitwise.service;

import com.example.splitwise.models.Amount;
import com.example.splitwise.models.Currency;
import com.example.splitwise.models.Expense;

import java.util.*;

public class ExpenseService {

    private final Map<String, Set<Expense>> expenseMap;
    private final UserBalanceService userBalanceService;

    public ExpenseService(UserBalanceService balanceService) {
        this.expenseMap = new HashMap<>();
        this.userBalanceService = balanceService;
    }

    public void addExpense(Expense expense) {
        final Set<String> userIDs = getExpenseUsers(expense);
        if (!userIDs.isEmpty()) {
            for (String userID : userIDs) {
                expenseMap.putIfAbsent(userID, new HashSet<>());
                expenseMap.get(userID).add(expense);
            }
            userBalanceService.balancePaymentGraph(expense);
        }

    }

    public void deleteExpense(Expense expense) {
        final Set<String> userIDs = getExpenseUsers(expense);
        if (!userIDs.isEmpty()) {
            for (String userID : userIDs) {
                expenseMap.get(userID).remove(expense);
                userBalanceService.removeFromBalances(expense);
            }
        }
    }

    public void editExpense(Expense prevExpense, Expense newExpense) {
        final Set<String> userIDs = getExpenseUsers(newExpense);
        if (!userIDs.isEmpty()) {
            for (String userID : userIDs) {
                expenseMap.get(userID).remove(prevExpense);
                expenseMap.get(userID).add(newExpense);
            }
            userBalanceService.removeFromBalances(prevExpense);
            userBalanceService.balancePaymentGraph(newExpense);
        }
    }


    // [u1, u2, u3] -> [u1: -10, u2 : -20, u3 : 30]

    // u1 -> {u3 : -10, u2 : 0}
    // u2 -> {u3 : -20, u1 : 0}
    // u3 -> {u1: 10, u2: 20}


    // [u1, u2, u3] - > {u1 : 10 u2 : 30 u3 : -40}

    // u1 -> {u3 : -10 + 10 u2 : 0}
    // u2 -> {u3 : -20 + 30 , u1 : 0}
    // u3 -> {u1 : 10 -10 , u2 : 20 - 30}

    Set<String> getExpenseUsers(Expense expense) {
        return expense.getUserBalances().getBalances().keySet();
    }


    public Set<Expense> getUserExpenses(String userID) {
        return expenseMap.get(userID);
    }


}
