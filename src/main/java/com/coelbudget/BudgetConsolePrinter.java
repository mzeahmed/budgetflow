package com.coelbudget;

import java.math.BigDecimal;
import java.util.List;

public class BudgetConsolePrinter {

    private static final String RESET = "\033[0m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String CYAN = "\033[0;36m";

    private BudgetConsolePrinter() {
    }

    public static void print(Budget budget) {
        BigDecimal foodAmounts = budget.calculateExpensesByCategory(Category.FOOD);
        BigDecimal balance = budget.calculateBalance();
        List<Transaction> transactions = budget.getTransactions();

        System.out.println(BLUE + "╔══════════════════════════════════════╗" + RESET);
        System.out.println(BLUE + "║          COELBUDGET — RÉSUMÉ         ║" + RESET);
        System.out.println(BLUE + "╚══════════════════════════════════════╝" + RESET);
        System.out.println(CYAN + "\nTransactions" + RESET);
        System.out.println("────────────────────────────────────────");

        for (Transaction transaction : transactions) {
            printTransaction(transaction);
        }

        System.out.println("────────────────────────────────────────");
        System.out.println(YELLOW + "Dépenses alimentation : " + foodAmounts + RESET);
        String balanceColor = balance.signum() >= 0 ? GREEN : RED;
        System.out.println(balanceColor + "Solde final           : " + balance + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "Erreur : " + message + RESET);
    }

    private static void printTransaction(Transaction transaction) {
        String color = transaction.getType() == TransactionType.INCOME ? GREEN : RED;
        String sign = transaction.getType() == TransactionType.INCOME ? "+" : "-";

        System.out.println(color
                + sign + " " + transaction.getDescription()
                + " [" + transaction.getCategory() + "]"
                + " : " + transaction.getAmount()
                + RESET);
    }
}
