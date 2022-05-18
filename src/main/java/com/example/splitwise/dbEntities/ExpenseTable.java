package com.example.splitwise.dbEntities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Expenses")
public class ExpenseTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long expenseId;

    @Id
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserTable userID;

    @Column(name = "time_stamp")
    private long time_stamp;

    @Column(name = "title")
    private String title;

    @Column(name = "balance")
    private double balance;


}
