package com.example.splitwise.service;

import com.example.splitwise.models.BalanceMap;
import com.example.splitwise.models.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final ExpenseService expenseService;
    private final UserBalanceService userBalanceService;
    Map<String, User> userMap;

    public UserService(ExpenseService expenseService, UserBalanceService balanceService) {
        this.expenseService = expenseService;
        this.userBalanceService = balanceService;
        this.userMap = new HashMap<>();
    }

    public void addUser(User user){
        userMap.put(String.valueOf(user.getUserID()), user);
    }

    public User getUserById(long id) {
        return userMap.get(String.valueOf(id));
    }

    public BalanceMap getUserBalances(final String userID) {
        return userBalanceService.getPaymentGraph(userID);
    }
}
