package com.example.splitwise.dbEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Expenses")
public class UserBalancesTable {

    @Id
    @Column(name = "primary_user_id")
    private String primaryUserID;

    @Id
    @Column(name = "secondary_user_id")
    private String secondaryUserID;

    @Column(name = "balance")
    private double balance;

}
