package com.ps;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static ArrayList<Transaction> transactions;
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        displayHomeScreen();
    }

    private static void displayHomeScreen() {
        String choice;
        do {
            System.out.println("Please select one of the following options");
            System.out.println("D)ADD DEPOSIT");
            System.out.println("P)MAKE PAYMENT");
            System.out.println("L)LEDGER");
            System.out.println("X)EXIT THE APPLICATION");

            while (true) {
                if (scanner.hasNextLine()) {
                    choice = scanner.nextLine();
                    break;
                } else {
                    System.out.print("Please enter a valid option: ");
                    scanner.nextLine();
                }
            }

            switch(choice){
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
                default:
                    System.out.println("Please enter one of the menu options");
                    scanner.nextLine();
            }

        }while(!choice.equalsIgnoreCase("x"));
    }

    private static void openLedger() {
    }

    private static void makePayment() {
    }

    private static void addDeposit() {
    }


}