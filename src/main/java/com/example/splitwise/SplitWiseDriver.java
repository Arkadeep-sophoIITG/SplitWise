package com.example.splitwise;

import com.example.splitwise.models.*;
import com.example.splitwise.service.ExpenseService;
import com.example.splitwise.service.UserBalanceService;
import com.example.splitwise.service.UserService;

import java.time.LocalDateTime;
import java.util.Set;

public class SplitWiseDriver {

    public static void main(String[] args){
        User user1 = new User("u1", "robin", "dfvdsvs");
        User user2 = new User("u2", "robin2", "dfvdsvs");
        User user3 = new User("u3", "robin3", "dfvdsvs");
        UserBalanceService balanceService = new UserBalanceService();
        ExpenseService expenseService = new ExpenseService(balanceService);
        UserService userService = new UserService(expenseService, balanceService);

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        BalanceMap map = new BalanceMap();
        map.getBalances().put(user1.getUserID(), new Amount(Currency.USD, -10));
        map.getBalances().put(user2.getUserID(), new Amount(Currency.USD, -20));
        map.getBalances().put(user3.getUserID(), new Amount(Currency.USD, 30));
        Expense expense = new Expense.ExpenseBuilder
                (map, "expense1", user1.getUserID(), "e1", LocalDateTime.now()).build();

        expenseService.addExpense(expense);
        BalanceMap loan = new BalanceMap();
        loan.getBalances().put(user1.getUserID(), new Amount(Currency.USD, 10));
        loan.getBalances().put(user2.getUserID(), new Amount(Currency.USD, 30));
        loan.getBalances().put(user3.getUserID(), new Amount(Currency.USD, -40));
        Expense expense2= new Expense.ExpenseBuilder
                (loan, "expense2", user2.getUserID(), "e2", LocalDateTime.now()).build();
        expenseService.addExpense(expense2);
        System.out.println("dfsdfsd");

        expenseService.deleteExpense(expense2);
        System.out.println("dfsdfsd");

    }

}
