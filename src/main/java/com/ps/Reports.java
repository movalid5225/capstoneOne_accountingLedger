package com.ps;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.ps.Actions.*;

public class Reports {
    static void searchByVendor() {
        System.out.print("Enter the name of the vendor you are searching for: ");
        String name = scanner.nextLine().toUpperCase();
        ArrayList<Transaction> vendorTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(name)) {
                t.printTransaction();
                vendorTransactions.add(t);
            }
        }

        if (!vendorTransactions.isEmpty()) {
            System.out.print("\nExport this vendor's transactions? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                exportTransactionListToFile(vendorTransactions, "Vendor Report");
            }
        }
    }





    static void previousYear() {
        int lastYear = LocalDate.now().getYear() - 1;

        LocalDate start = LocalDate.of(lastYear, 1, 1);
        LocalDate end = LocalDate.of(lastYear, 12, 31);
        ArrayList<Transaction> yearTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(start) && !t.getDate().isAfter(end)) {
                t.printTransaction();
                yearTransactions.add(t);
            }
        }

        if (!yearTransactions.isEmpty()) {
            System.out.print("\nExport last year's transactions? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                exportTransactionListToFile(yearTransactions, "Previous Year Report");
            }
        }
    }





    static void yearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = today.withDayOfYear(1);
        ArrayList<Transaction> yearTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(startOfYear) && !t.getDate().isAfter(today)) {
                t.printTransaction();
                yearTransactions.add(t);
            }
        }

        if (!yearTransactions.isEmpty()) {
            System.out.print("\nExport this year's transactions? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                exportTransactionListToFile(yearTransactions, "Year to Date Report");
            }
        }
    }





    static void previousMonth() {
        LocalDate firstOfCurrMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate firstOfPrevMonth = firstOfCurrMonth.minusMonths(1);
        LocalDate lastOfPrevMonth = firstOfPrevMonth.withDayOfMonth(firstOfPrevMonth.lengthOfMonth());
        ArrayList<Transaction> monthTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(firstOfPrevMonth) && !t.getDate().isAfter(lastOfPrevMonth)) {
                t.printTransaction();
                monthTransactions.add(t);
            }
        }

        if (!monthTransactions.isEmpty()) {
            System.out.print("\nExport last month's transactions? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                exportTransactionListToFile(monthTransactions, "Previous Month Report");
            }
        }
    }





    static void monthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        ArrayList<Transaction> monthTransactions = new ArrayList<>();

        for (Transaction t : transactions) {
            if (!t.getDate().isBefore(monthStart) && !t.getDate().isAfter(today)) {
                t.printTransaction();
                monthTransactions.add(t);
            }
        }

        if (!monthTransactions.isEmpty()) {
            System.out.print("\nExport this month's transactions? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                exportTransactionListToFile(monthTransactions, "Month to Date Report");
            }
        }
    }





    public static void viewCategorySpending() {
        while(true) {
            short choice;
            while (true) {
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

            switch (choice) {
                case 1:
                    viewMonthlySpending();
                    return;
                case 2:
                    viewYearlySpending();
                    return;
            }
        }
    }




//  Method to view spending divided into categories for any year
    private static void viewYearlySpending() {
        System.out.print("Enter the year you'd like to view: ");
        int year;
        while (true) {
            try {
                year = Integer.parseInt(scanner.nextLine());
                if (year < 1900 || year > LocalDate.now().getYear()) {
                    System.out.print("Please enter a valid year: ");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a numeric year: ");
            }
        }

        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        HashMap<String, Double> payByCategoryMap = new HashMap<>();

        for (Transaction t : transactions) {
            LocalDate date = t.getDate();
            if (!date.isBefore(start) && !date.isAfter(end) && t.getAmount() < 0) {
                payByCategoryMap.put(
                        t.getCategory(),
                        payByCategoryMap.getOrDefault(t.getCategory(), 0.0) + Math.abs(t.getAmount())
                );
            }
        }

        System.out.println("\n========== YEARLY CATEGORY SPENDING ==========");
        System.out.println("Year: " + year);
        if (payByCategoryMap.isEmpty()) {
            System.out.println("No payments found for this year.");
        } else {
            for (Map.Entry<String, Double> entry : payByCategoryMap.entrySet()) {
                System.out.printf("%-25s : $%.2f\n", entry.getKey(), entry.getValue());
            }

            System.out.print("\nWould you like to export this report to a file? (Y/N): ");
            String exportChoice = scanner.nextLine();
            if (exportChoice.equalsIgnoreCase("Y")) {
                exportCategoryReport(payByCategoryMap, "Yearly Category Spending", start, end);
            }
        }
        System.out.println("===============================================\n");
    }





//  Method to view spending divided into categories for any month
    private static void viewMonthlySpending() {
        int year;
        int month;

        while (true) {
            System.out.print("Enter the year: ");
            try {
                year = Integer.parseInt(scanner.nextLine());
                if (year < 1900 || year > LocalDate.now().getYear()) {
                    System.out.println("Please enter a realistic year between 1900 and the current year.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric year like 2025.");
            }
        }


        while (true) {
            System.out.print("Enter the month (1-12): ");
            try {
                month = Integer.parseInt(scanner.nextLine());
                if (month < 1 || month > 12) {
                    System.out.println("Month must be between 1 and 12.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 12.");
            }
        }

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        HashMap<String, Double> payByCategoryMap = new HashMap<>();

        for (Transaction t : transactions) {
            LocalDate date = t.getDate();
            if (!date.isBefore(start) && !date.isAfter(end) && t.getAmount() < 0) {
                payByCategoryMap.put(t.getCategory(), payByCategoryMap.getOrDefault(t.getCategory(), 0.0) + Math.abs(t.getAmount()));
            }
        }

        System.out.println("\n========== CATEGORY SPENDING ==========");
        System.out.println("For: " + start.getMonth() + " " + year);
        if (payByCategoryMap.isEmpty()) {
            System.out.println("No payments found.");
        } else {
            for (Map.Entry<String, Double> entry : payByCategoryMap.entrySet()) {
                System.out.printf("%-25s : $%.2f\n", entry.getKey(), entry.getValue());
            }
            System.out.print("\nWould you like to export this report to a file? (Y/N): ");
            String exportChoice = scanner.nextLine();
            if (exportChoice.equalsIgnoreCase("Y")) {
                exportCategoryReport(payByCategoryMap, "Monthly Category Spending", start, end);
            }
        }
        System.out.println("=======================================\n");
    }




// Method to write report to a new file with hashmap input
    private static void exportCategoryReport(HashMap<String, Double> categoryMap, String title, LocalDate start, LocalDate end) {
        String fileName = title.toLowerCase().replace(" ", "-") + "-" + start.getYear() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(title + "\n");
            writer.write("Period: " + start + " to " + end + "\n");
            writer.write("=====================================\n");

            for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
                String category = entry.getKey();
                double total = entry.getValue();
                writer.write(String.format("%-25s : $%.2f\n", category, total));
            }

            writer.write("=====================================\n");
            System.out.println("Report saved as: " + fileName + "\n");
        } catch (Exception e) {
            System.out.println("Failed to write report to file.");
        }
    }




// Method to write report to a new file with arraylist input
    private static void exportTransactionListToFile(ArrayList<Transaction> list, String title) {
        String fileName = title.toLowerCase().replace(" ", "-") + "-" + LocalDate.now() + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(title + "\n");
            writer.write("Exported on: " + LocalDate.now() + "\n");
            writer.write("=====================================\n");

            for (Transaction t : list) {
                writer.write(String.format("%s | %s | %s | %s | $%.2f | %s\n",
                        t.getDate(),
                        formatTime(t.getTime()),
                        t.getDescription(),
                        t.getVendor(),
                        t.getAmount(),
                        t.getCategory()
                ));
            }

            writer.write("=====================================\n");
            System.out.println("Report saved to: " + fileName);
        } catch (Exception e) {
            System.out.println("Failed to write report to file.");
        }
    }
}
