package com.ps;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Actions {
    public static final ArrayList<Transaction> transactions = new ArrayList<>();
    public static final Scanner scanner = new Scanner(System.in);




//  Load text file into transactions array
    public static void loadTransactions(){
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader("transactions.txt"));
            buffReader.readLine();
            String input;
            while((input = buffReader.readLine()) != null ) {
                String[] line = input.split("\\|");
                LocalDate date = LocalDate.parse(line[0]);
                LocalTime time = LocalTime.parse(line[1]);
                Transaction transaction = new Transaction(date, time, line[2], line[3], Double.parseDouble(line[4]), line[5]);
                transactions.add(transaction);
            }

            transactions.sort((t1, t2) -> {
                int comparison = t1.getDate().compareTo(t2.getDate());
                if (comparison == 0) {
                    return t1.getTime().compareTo(t2.getTime());
                }
                return comparison;
            });

            buffReader.close();
        }
        catch(Exception e){
            System.out.println("COULD NOT READ FILE");
        }
    }





//  Home screen
    public static void displayHomeScreen() {
        String choice;
        do {
            System.out.println("\n====================================");
            System.out.println("*** WELCOME TO YOUR BANK ACCOUNT ***");
            System.out.println("====================================\n");

            System.out.println("==========================");
            System.out.println("D)ADD DEPOSIT");
            System.out.println("P)MAKE PAYMENT");
            System.out.println("L)LEDGER");
            System.out.println("X)EXIT THE APPLICATION");
            System.out.println("==========================\n");

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





    //  Home screen functions
    private static void addDeposit() {
        String transactionType = "DEPOSIT";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeStr = time.truncatedTo(ChronoUnit.SECONDS).toString();

        System.out.println("\n==================================");
        System.out.println("ARE YOUR READY TO MAKE A DEPOSIT?");
        System.out.println("==================================\n");
        System.out.print("ENTER THE DESCRIPTION OF YOUR DEPOSIT: ");
        String description = scanner.nextLine();

        System.out.print("ENTER THE NAME OF THE VENDOR: ");
        String name = scanner.nextLine();

        double amount;
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

        String category;
        short categoryNum;
        while(true) {
            System.out.println("ENTER CATEGORY OF YOUR TRANSACTION");
            System.out.println("1)HOUSING");
            System.out.println("2)FOOD");
            System.out.println("3)TRANSPORTATION");
            System.out.println("4)HEALTH & PERSONAL");
            System.out.println("5)LIFESTYLE & ENTERTAINMENT");
            System.out.println("6)MISCELLANEOUS");
            try{
                categoryNum = Short.parseShort(scanner.nextLine());
                while(categoryNum > 6 || categoryNum < 1){
                    System.out.print("ENTER A VALID OPTION: ");
                    categoryNum = Short.parseShort(scanner.nextLine());
                }
                category = convertToCategory(categoryNum);
                break;
            }catch(NumberFormatException e){
                System.out.println("Enter a valid number option");
            }
        }

        Transaction transaction = new Transaction(date, time, description, name, amount, category);
        transactions.add(transaction);

        writeTransaction(date, timeStr, description, name, amount,category,transactionType, transaction);
    }





    private static void makePayment() {
        String transactionType = "PAYMENT";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String timeStr = time.truncatedTo(ChronoUnit.SECONDS).toString();

        System.out.println("\n==================================");
        System.out.println("ARE YOUR READY TO MAKE A PAYMENT?");
        System.out.println("==================================\n");

        System.out.print("ENTER THE DESCRIPTION OF YOUR PAYMENT: ");
        String description = scanner.nextLine();

        System.out.print("ENTER THE NAME OF THE VENDOR: ");
        String name = scanner.nextLine();

        double amount;
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
        String category;
        short categoryNum;
        while(true) {
            System.out.println("ENTER CATEGORY OF YOUR TRANSACTION");
            System.out.println("1)HOUSING");
            System.out.println("2)FOOD");
            System.out.println("3)TRANSPORTATION");
            System.out.println("4)HEALTH & PERSONAL");
            System.out.println("5)LIFESTYLE & ENTERTAINMENT");
            System.out.println("6)MISCELLANEOUS");
            try{
                categoryNum = Short.parseShort(scanner.nextLine());
                while(categoryNum > 6 || categoryNum < 1){
                    System.out.print("ENTER A VALID OPTION: ");
                    categoryNum = Short.parseShort(scanner.nextLine());
                }
                category = convertToCategory(categoryNum);
                break;
            }catch(NumberFormatException e){
                System.out.println("Enter a valid number option");
            }
        }
        amount *= -1;
        Transaction transaction = new Transaction(date, time, description, name, amount, category);
        transactions.add(transaction);

        writeTransaction(date, timeStr, description, name, amount,category, transactionType, transaction);
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





//  Helper functions
    public static String formatTime(LocalTime time) {
        return time.truncatedTo(ChronoUnit.MINUTES).format(java.time.format.DateTimeFormatter.ofPattern("hh:mm a"));
    }






    private static String convertToCategory(short choice){
        return switch (choice) {
            case 1 -> "HOUSING";
            case 2 -> "FOOD";
            case 3 -> "TRANSPORTATION";
            case 4 -> "HEALTH & PERSONAL";
            case 5 -> "LIFESTYLE & ENTERTAINMENT";
            case 6 -> "MISCELLANEOUS";
            default -> "null";
        };
    }





    private static void writeTransaction(LocalDate date, String timeStr, String description ,String name, double amount,String category ,String transactionType, Transaction transaction){
        try {
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter("Transactions.txt", true));

            String line = date + "|" + timeStr + "|" + description.toUpperCase() + "|" + name.toUpperCase() + "|" + amount+ "|" + category;
            buffWriter.write(line);
            buffWriter.newLine();
            buffWriter.close();
            System.out.print("\n\nProcessing");
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Error with .sleep method");
                }
                System.out.print(".");
            }
            System.out.println();
            transaction.printTransaction();
            System.out.println("\n======================");
            System.out.println("** " +transactionType+ " SUCCESSFUL **");
            System.out.println("======================\n");
        }
        catch(Exception e){
            System.out.println("Could not write to file");
        }
    }





//  Ledger functions
    private static void displayReports() {
        while(true) {
            System.out.println("SELECT A REPORT");
            System.out.println("1)MONTH TO DATE");
            System.out.println("2)PREVIOUS MONTH");
            System.out.println("3)YEAR TO DATE");
            System.out.println("4)PREVIOUS YEAR");
            System.out.println("5)SEARCH BY VENDOR");
            System.out.println("6)VIEW CATEGORY SPENDING");
            System.out.println("0)GO BACK TO HOME");

            short choice;
            while (true) {
                try {
                    choice = Short.parseShort(scanner.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.print("PLEASE ENTER A VALID OPTION: ");
                }
            }

            switch (choice) {
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
                case 6:
                    Reports.viewCategorySpending();
                    break;
                case 0:
                    System.out.println("\n\nGOING BACK.....");
                    return;
                default:
                    System.out.println("ENTER A VALID OPTION");
            }
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
