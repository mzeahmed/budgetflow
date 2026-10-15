package com.coelbudget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a collection of financial transactions.
 *
 * <p>Provides operations to calculate the overall balance
 * and aggregate expenses by category.</p>
 */
public class Budget {

    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Adds a transaction to the budget.
     *
     * @param transaction the transaction to add
     * @throws IllegalArgumentException if transaction is null
     */
    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }

        transactions.add(transaction);
    }

    /**
     * Calculates the current budget balance.
     *
     * <p>Income amounts are added and expense amounts are subtracted.</p>
     *
     * @return the resulting balance, or zero if the budget is empty
     */
    public BigDecimal calculateBalance() {
        BigDecimal balance = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.INCOME) {
                balance = balance.add(transaction.getAmount());
            } else if (transaction.getType() == TransactionType.EXPENSE) {
                balance = balance.subtract(transaction.getAmount());
            }
        }

        return balance;
    }

    /**
     * Returns an unmodifiable snapshot of the budget transactions.
     *
     * <p>Changes to the internal transaction list do not affect
     * the returned list. Transaction objects themselves are not copied.</p>
     *
     * @return an unmodifiable list of transactions
     */
    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }

    /**
     * Calculates the total expenses for a given category.
     *
     * <p>Only transactions of type {@link TransactionType#EXPENSE}
     * are included in the calculation. Income transactions are ignored.</p>
     *
     * @param category the category used to filter expenses
     * @return the total expense amount, or zero if no expenses match
     * @throws IllegalArgumentException if category is null
     */
    public BigDecimal calculateExpensesByCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        BigDecimal total = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {

            if (transaction.getCategory() == category && transaction.getType() == TransactionType.EXPENSE) {
                total = total.add(transaction.getAmount());
            }

        }

        return total;
    }

    /**
     * Calculates expense totals for all transaction categories.
     *
     * <p>The returned map contains every {@link Category} value,
     * including categories with no expenses, which have a zero total.</p>
     *
     * @return a map associating each category with its total expenses
     */
    public Map<Category, BigDecimal> calculateExpensesByCategories() {
        Map<Category, BigDecimal> expenses = new EnumMap<>(Category.class);

        for (Category category : Category.values()) {
            expenses.put(category, this.calculateExpensesByCategory(category));
        }

        return expenses;
    }
}
