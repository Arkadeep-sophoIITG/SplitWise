package com.example.splitwise.models;

import javax.persistence.Entity;

public class User {
    private final String userID;
    private final String name;
    private final String dateOfBirth;

    public User(String userID, String name, String dateOfBirth) {
        this.userID = userID;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
