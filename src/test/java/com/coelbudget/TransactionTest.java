package com.coelbudget;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {

    @Test
    void shouldCreateValidTransaction() {
        // 1. Créer une transaction "Salaire"
        // Montant : 2500.00
        // Type : INCOME
        BigDecimal salaryAmount = new BigDecimal("2500.00");
        Transaction salary = new Transaction("Salaire", salaryAmount, TransactionType.INCOME);

        // 2. Vérifier la description avec assertEquals()
        assertEquals("Salaire", salary.getDescription());

        // 3. Vérifier le montant avec assertEquals()
        assertEquals(new BigDecimal("2500.00"), salary.getAmount());

        // 4. Vérifier le type avec assertEquals()
        assertEquals(TransactionType.INCOME, salary.getType());
    }
}
