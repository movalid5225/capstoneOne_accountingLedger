package com.ps;
import org.w3c.dom.ls.LSOutput;

import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Actions {
    private static final ArrayList<Transaction> transactions = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayHomeScreen() {
        String choice;
        do {
            System.out.println("D)ADD DEPOSIT");
            System.out.println("P)MAKE PAYMENT");
            System.out.println("L)LEDGER");
            System.out.println("X)EXIT THE APPLICATION");

            choice = scanner.nextLine();
            choice = choice.toUpperCase();
            switch(choice) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    openLedger();
                    break;
                case "X":
                    System.out.println("\nExiting the application.....\n\nHave a great day!\n");
                    break;
                default:
                    System.out.println("Please enter one of the menu options");
            }

        }while(!choice.equalsIgnoreCase("x"));
    }


    //  HomeScreen functions
    private static void addDeposit() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeStr = time.truncatedTo(ChronoUnit.SECONDS).toString();

        System.out.print("ENTER THE DESCRIPTION OF YOUR ITEM: ");
        String description = scanner.nextLine();

        System.out.print("ENTER THE NAME OF THE VENDOR: ");
        String name = scanner.nextLine();

        double amount = 0;
        while (true) {
            System.out.print("ENTER YOUR DEPOSIT AMOUNT: ");
            String amountStr = scanner.nextLine();

            try {
                amount = Double.parseDouble(amountStr);
                while(amount <= 0){
                    System.out.print("DEPOSIT MUST BE OVER 0: ");
                    amount = Double.parseDouble(scanner.nextLine());
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("INVALID INPUT. PLEASE ENTER A VALID NUMBER\n");
            }
        }

        Transaction transaction = new Transaction(date, time, description, name, amount);
        transactions.add(transaction);

        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Transactions.txt", true));

            String line = date + "|" + timeStr + "|" + description + "|" + name + "|" + amount;
            buffWriter.write(line);
            buffWriter.newLine();

            buffWriter.close();
            System.out.print("\n\nProcessing");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(".");
            }
            System.out.println("\n");
            transaction.printTransaction();
            System.out.println("\n\n======================");
            System.out.println("**DEPOSIT SUCCESSFUL**");
            System.out.println("======================\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void makePayment() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeStr = time.truncatedTo(ChronoUnit.SECONDS).toString();

        System.out.print("ENTER THE DESCRIPTION OF YOUR ITEM: ");
        String description = scanner.nextLine();

        System.out.print("ENTER THE NAME OF THE VENDOR: ");
        String name = scanner.nextLine();

        double amount = 0;
        while(true ){
            System.out.print("ENTER YOUR PAYMENT AMOUNT: ");
            String amountStr = scanner.nextLine();

            try{
                amount = Double.parseDouble(amountStr);
                while(amount <= 0){
                    System.out.print("PAYMENT AMOUNT MUST BE OVER 0: ");
                    amount = Double.parseDouble(scanner.nextLine());
                }
                break;
            }
            catch(NumberFormatException e){
                System.out.println("INVALID INPUT. PLEASE ENTER A VALID NUMBER");
            }
        }

        amount *= -1;
        Transaction transaction = new Transaction(date, time, description, name, (amount));
        transactions.add(transaction);

        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Transactions.txt", true));

            String line = date + "|" + timeStr + "|" + description + "|" + name + "|" + amount;
            buffWriter.write(line);
            buffWriter.newLine();
            buffWriter.close();
            System.out.print("\n\nProcessing");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.print(".");
            }

            System.out.println("\n\n\n======================");
            System.out.println("** PAYMENT SUCCESSFUL **");
            System.out.println("======================\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void openLedger() {
        while(true) {
            System.out.println("SELECT AN OPTION");
            System.out.println("A) All");
            System.out.println("D) Deposits");
            System.out.println("P) Payments");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            String choice = scanner.nextLine();
            choice = choice.toUpperCase();

            switch (choice) {
                case "A":
                    displayAllTransactions();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    displayReports();
                    break;
                case "H":
                    System.out.println("\nRETURNING HOME...\n");
                    return;
                default:
                    System.out.println("Please try again");
            }
        }
    }

//  Ledger functions
    private static void displayReports() {
        System.out.println("SELECT A REPORT");
        System.out.println("1)MONTH TO DATE");
        System.out.println("2)PREVIOUS MONTH");
        System.out.println("3)YEAR TO DATE");
        System.out.println("4)PREVIOUS YEAR");
        System.out.println("5)SEARCH BY VENDOR");
        System.out.println("0)GO BACK TO HOME");

        short choice = 0;
        while(true){
            try{
                choice = Short.parseShort(scanner.nextLine());
                break;
            }
            catch(Exception e){
                System.out.print("PLEASE ENTER A VALID OPTION: ");
            }
        }

        switch(choice){
            case 1:
                Reports.monthToDate();
                break;
            case 2:
                Reports.previousMonth();
                break;
            case 3:
                Reports.yearToDate();
                break;
            case 4:
                Reports.previousYear();
                break;
            case 5:
                Reports.searchByVendor();
                break;
            case 0:
                System.out.println("\n\nGOING BACK.....");
        }
    }


    private static void displayPayments() {
        for(Transaction transaction : transactions){
            if(transaction.getAmount() <  0){
                transaction.printTransaction();
            }
        }
    }

    private static void displayDeposits() {
        for(Transaction transaction : transactions){
            if(transaction.getAmount() > 0){
                transaction.printTransaction();
            }
        }
    }

    private static void displayAllTransactions() {
        for(Transaction transaction : transactions){
            transaction.printTransaction();
        }
    }

}
