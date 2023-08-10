package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transactions;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;


public class TransactionDTO {
    private long id;
    private TransactionType type;

    private LocalDate date;

    private double amount;
    private String description;
    public TransactionDTO(Transactions transactions){
        id= transactions.getId();
        type = transactions.getType();
        date = transactions.getDate();
        amount = transactions.getAmount();
        description = transactions.getDescription();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
