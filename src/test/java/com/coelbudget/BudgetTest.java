package com.coelbudget;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BudgetTest {

    @Test
    void shouldHaveZeroBalanceWhenEmpty() {
        Budget budget = new Budget();

        BigDecimal balance = budget.calculateBalance();

        assertEquals(0, balance.compareTo(BigDecimal.ZERO));
    }

    @Test
    void shouldIncreaseBalanceWhenAddingIncome() {
        Transaction transaction = new Transaction(
                "Salaire",
                new BigDecimal("2500.00"),
                TransactionType.INCOME
        );
        Budget budget = new Budget();

        budget.addTransaction(transaction);
        BigDecimal balance = budget.calculateBalance();

        assertEquals(new BigDecimal("2500.00"), balance);
    }
}