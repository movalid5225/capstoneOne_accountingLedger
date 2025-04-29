package com.ps;

public class Transaction {
    private String date;
    private String time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction() {
        this.amount = 0;
        this.vendor = "";
        this.time = "";
        this.description = "";
        this.date = "";
    }

//  Parametrized constructor
    public Transaction(double amount, String vendor, String time, String description, String date) {
        this.amount = amount;
        this.vendor = vendor;
        this.time = time;
        this.description = description;
        this.date = date;
    }

//  Getters and Setters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
