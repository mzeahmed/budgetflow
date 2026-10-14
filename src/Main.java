import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        Budget budget = new Budget();

        try {
            BigDecimal salaryAmount = new BigDecimal("2500.00");
            BigDecimal rentAmount = new BigDecimal("850.00");
            BigDecimal groceryAmount = new BigDecimal("150.00");
            BigDecimal bonusAmount = new BigDecimal("300.00");

            Transaction rent = new Transaction("Loyer", rentAmount, TransactionType.EXPENSE);
            Transaction salary = new Transaction("Salaire", salaryAmount, TransactionType.INCOME);
            Transaction grocery = new Transaction("Courses", groceryAmount, TransactionType.EXPENSE);
            Transaction bonus = new Transaction("Prime", bonusAmount, TransactionType.INCOME);

            budget.addTransaction(salary);
            budget.addTransaction(rent);
            budget.addTransaction(grocery);
            budget.addTransaction(bonus);

            BigDecimal balance = budget.calculateBalance();

            displayTransaction(salary);
            displayTransaction(rent);
            displayTransaction(grocery);
            displayTransaction(bonus);

            System.out.println("\nbalance : " + balance);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }


    }

    public static void displayTransaction(Transaction transaction) {
        System.out.println(
                "Description : " + transaction.getDescription()
                        + "\nMontant : " + transaction.getAmount()
                        + "\nType : " + transaction.getType()
        );
    }
}