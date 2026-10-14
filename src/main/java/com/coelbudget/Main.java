package com.coelbudget;

import java.math.BigDecimal;

public class Main {

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

            BudgetConsolePrinter.print(budget);
        } catch (IllegalArgumentException e) {
            BudgetConsolePrinter.printError(e.getMessage());
        }
    }
}
