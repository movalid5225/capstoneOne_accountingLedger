package com.ps;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;


public class Main {
    private static ArrayList<Transaction> transactions;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        displayHomeScreen();
    }

    private static void displayHomeScreen() {
        String choice;
        do {
            System.out.println("D)ADD DEPOSIT");
            System.out.println("P)MAKE PAYMENT");
            System.out.println("L)LEDGER");
            System.out.println("X)EXIT THE APPLICATION");

            choice = scanner.nextLine();
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
                    System.out.println("\nExiting the application...\n Have a great day!\n");
                    break;
                default:
                    System.out.println("Please enter one of the menu options");
            }

        }while(!choice.equalsIgnoreCase("x"));
    }

    private static void openLedger() {
        System.out.println("SELECT AN OPTION");
        System.out.println("A) All");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Home");
        String choice = scanner.nextLine();

        switch(choice){
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
        }
    }

    private static void displayReports() {
    }

    private static void displayPayments() {
    }

    private static void displayDeposits() {
    }

    private static void displayAllTransactions() {
    }

    private static void makePayment() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeStr = time.truncatedTo(ChronoUnit.SECONDS).toString();

        System.out.println("ENTER THE DESCRIPTION OF YOUR ITEM: ");
        String description = scanner.nextLine();

        System.out.println("ENTER THE NAME OF THE VENDOR: ");
        String name = scanner.nextLine();

        System.out.println("ENTER YOUR PAYMENT AMOUNT: ");
        double amount = (Double.parseDouble(scanner.nextLine()))*-1;

        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Transactions.txt", true));

            String line = date + "|" + timeStr + "|" + description + "|" + name + "|" + amount;
            buffWriter.write(line);
            buffWriter.newLine();
            buffWriter.close();
            System.out.println("\nDEPOSIT SUCCESSFUL\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void addDeposit() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeStr = time.truncatedTo(ChronoUnit.SECONDS).toString();

        System.out.println("ENTER THE DESCRIPTION OF YOUR ITEM: ");
        String description = scanner.nextLine();

        System.out.println("ENTER THE NAME OF THE VENDOR: ");
        String name = scanner.nextLine();

        System.out.println("ENTER YOUR DEPOSIT AMOUNT: ");
        double amount = Double.parseDouble(scanner.nextLine());

        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Transactions.txt", true));

            String line = date + "|" + timeStr + "|" + description + "|" + name + "|" + amount;
            buffWriter.write(line);
            buffWriter.newLine();

            buffWriter.close();
            System.out.println("\nDEPOSIT SUCCESSFUL\n");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void recordTransaction(){

    }


}