package com.ps;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Formattable;
import java.util.HashMap;
import java.util.Map;

import static com.ps.Actions.scanner;
import static com.ps.Actions.transactions;

public class Reports {
    public static void searchByVendor() {
            System.out.print("Enter the name of the vendor you are searching for: ");
            String name = scanner.nextLine().toUpperCase();

            for (Transaction t : transactions) {
                if (t.getVendor().equalsIgnoreCase(name)) {
                    t.printTransaction();
                }
            }
    }

    public static void previousYear() {
        int lastYear = LocalDate.now().getYear()-1;

        LocalDate start = LocalDate.of(lastYear,1,1);
        LocalDate end = LocalDate.of(lastYear,12,31);

        for (Transaction t : transactions){
            if(!t.getDate().isBefore(start) && !t.getDate().isAfter(end)){
                t.printTransaction();
            }
        }
    }





    public static void yearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.withDayOfYear(1);

        for (Transaction t : transactions){
            if(!t.getDate().isBefore(startOfYear) && !t.getDate().isAfter(today)){
                t.printTransaction();
            }
        }
    }




// buggy
    public static void previousMonth() {
        LocalDate firstOfCurrMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate firstOfPrevMonth = firstOfCurrMonth.minusMonths(1);
        LocalDate lastOfPrevMonth = firstOfPrevMonth.withDayOfMonth(firstOfPrevMonth.lengthOfMonth());

        for(Transaction t : transactions){
            if(!t.getDate().isBefore(firstOfPrevMonth) && !t.getDate().isAfter(lastOfPrevMonth)){
                t.printTransaction();
            }
        }
    }



    public static void monthToDate() {
        LocalDate today  = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);

        for(Transaction t : transactions){
            if(!t.getDate().isBefore(monthStart) && !t.getDate().isAfter(today)){
                t.printTransaction();
            }
        }
    }

    public static void viewCategorySpending() {

        short choice;
        while(true) {
            System.out.println("SELECT AN OPTION");
            System.out.println("1)VIEW MONTHLY SPENDING");
            System.out.println("2)VIEW YEARLY SPENDING");
            try {
                choice = Short.parseShort(scanner.nextLine());
                while (choice > 2 || choice < 1) {
                    System.out.print("ENTER A VALID OPTION: ");
                    choice = Short.parseShort(scanner.nextLine());
                }
                break;
            } catch (Exception e) {
                System.out.println("ENTER A VALID NUMBER");
            }
        }

        switch(choice){
            case 1:
                viewMonthlySpending();
                break;
            case 2:
                viewYearlySpending();
                break;
        }
    }

    private static void viewYearlySpending() {

    }

    private static void viewMonthlySpending() {
        HashMap<String, Double> payByCategoryMap = new HashMap<>();
        ArrayList<Transaction> monthlyPayments = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate firstOfMonth = today.withDayOfMonth(1);

        for(Transaction t : transactions){
            if((!t.getDate().isBefore(firstOfMonth) && !t.getDate().isAfter(today) && (t.getAmount() < 0))){
                monthlyPayments.add(t);
            }
        }


        for(Transaction t : monthlyPayments){
            payByCategoryMap.put(t.getCategory(), payByCategoryMap.getOrDefault(t.getCategory(), 0.0) + Math.abs(t.getAmount()));
        }
        System.out.println();

        System.out.println("\n========== CATEGORY SPENDING ==========");
        for(Map.Entry<String, Double> entry : payByCategoryMap.entrySet()){
            String cat = entry.getKey();
            Double totalPayment = entry.getValue();

            System.out.println(cat + ": " + totalPayment);
        }
        System.out.println("=======================================\n");
    }
}
