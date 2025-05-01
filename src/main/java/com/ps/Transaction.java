package com.ps;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private String category;

    public Transaction() {
        this.amount = 0;
        this.vendor = "";
        this.time = null;
        this.description = "";
        this.date = null;
        this.category = "";
    }

//  Parametrized constructor
    public Transaction(LocalDate date, LocalTime time, String description, String vendor ,double amount, String category) {
        this.amount = amount;
        this.vendor = vendor;
        this.time = time;
        this.description = description;
        this.date = date;
        this.category = category;
    }

//  Getters and Setters
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void printTransaction(){
        String timeStr = this.getTime().truncatedTo(ChronoUnit.SECONDS).toString();
        System.out.println("-------------------------------");
        System.out.println("Date       : " + this.getDate());
        System.out.println("Time       : " + timeStr);
        System.out.println("Description: " + this.getDescription());
        System.out.println("Vendor     : " + this.getVendor());

        String formattedAmount = String.format("+$%.2f", this.getAmount());
        System.out.println("Amount     : " + formattedAmount);
        System.out.println("Category   : " + this.getCategory());
        System.out.println("-------------------------------");
    }

}
