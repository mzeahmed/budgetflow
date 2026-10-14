package com.coelbudget;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    private static final String RESET = "\033[0m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String CYAN = "\033[0;36m";

    public static void main(String[] args) {

        Budget budget = new Budget();

        try {
            BigDecimal salaryAmount = new BigDecimal("2500.00");
            BigDecimal rentAmount = new BigDecimal("850.00");
            BigDecimal groceryAmount = new BigDecimal("150.00");
            BigDecimal restaurantAmount = new BigDecimal("60.00");

            Transaction rent = new Transaction("Loyer", rentAmount, TransactionType.EXPENSE, Category.HOUSING);
            Transaction salary = new Transaction("Salaire", salaryAmount, TransactionType.INCOME, Category.SALARY);
            Transaction grocery = new Transaction("Courses", groceryAmount, TransactionType.EXPENSE, Category.FOOD);
            Transaction restaurant = new Transaction("Restaurant", restaurantAmount, TransactionType.EXPENSE, Category.FOOD);

            budget.addTransaction(salary);
            budget.addTransaction(rent);
            budget.addTransaction(grocery);
            budget.addTransaction(restaurant);

            displayBudgetSummary(budget);
        } catch (IllegalArgumentException e) {
            System.out.println(RED + "Erreur : " + e.getMessage() + RESET);
        }
    }

    private static void displayBudgetSummary(Budget budget) {
        BigDecimal foodAmounts = budget.calculateExpensesByCategory(Category.FOOD);
        BigDecimal balance = budget.calculateBalance();
        List<Transaction> transactions = budget.getTransactions();

        System.out.println(BLUE + "╔══════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║          COELBUDGET — RÉSUMÉ         ║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════╝" + RESET);
        System.out.println(CYAN + "\nTransactions" + RESET);
        System.out.println("────────────────────────────────────────");

        for (Transaction transaction : transactions) {
            displayTransaction(transaction);
        }

        System.out.println("────────────────────────────────────────");
        System.out.println(YELLOW + "Dépenses alimentation : " + foodAmounts + RESET);
        String balanceColor = balance.signum() >= 0 ? GREEN : RED;
        System.out.println(balanceColor + "Solde final           : " + balance + RESET);
    }

    private static void displayTransaction(Transaction transaction) {
        String color = transaction.getType() == TransactionType.INCOME ? GREEN : RED;
        String sign = transaction.getType() == TransactionType.INCOME ? "+" : "-";

        System.out.println(color
                + sign + " " + transaction.getDescription()
                + " [" + transaction.getCategory() + "]"
                + " : " + transaction.getAmount()
                + RESET);
    }
}
