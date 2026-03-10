package com.example.ECommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Payment {

    @Id
    private String id;
    private int amount;
    private String currency;
    private String status;

    public Payment() {}

    public Payment(String id, int amount, String currency, String status) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}