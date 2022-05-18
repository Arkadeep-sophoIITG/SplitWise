package com.example.splitwise.models;

import javax.persistence.Entity;
import java.time.LocalDateTime;

public class Expense {
    private final BalanceMap userBalances;
    private final String title;
    private final String imageUrl;
    private final String description;
    private final String addingUserID;
    private final String expenseID;
    private final LocalDateTime dateTime;


    public Expense(ExpenseBuilder builder) {
        this.userBalances = builder.balanceMap;
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.description = builder.description;
        this.expenseID = builder.expenseID;
        this.addingUserID = builder.userID;
        this.dateTime = builder.dateTime;
    }


    public String getExpenseID() {
        return expenseID;
    }

    public BalanceMap getUserBalances() {
        return userBalances;
    }

    public String getAddingUserID() {
        return addingUserID;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "userBalances=" + userBalances +
                ", title='" + title + '\'' +
                '}';
    }

    public static class ExpenseBuilder {
        private final BalanceMap balanceMap;
        private final String title;
        private String imageUrl;
        private String description;
        private final String userID;
        private final String expenseID;
        private final LocalDateTime dateTime;

        public ExpenseBuilder(BalanceMap balanceMap, String title, String userID, String expenseID, LocalDateTime dateTime) {
            this.balanceMap = balanceMap;
            this.title = title;
            this.userID = userID;
            this.expenseID = expenseID;
            this.dateTime = dateTime;
        }

        public ExpenseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ExpenseBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Expense build() {
            Expense expense = new Expense(this);
            return expense;
        }
    }
}


