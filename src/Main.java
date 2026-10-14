import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {

        try {
            BigDecimal rentAmount = new BigDecimal("-850.00");
            Transaction rent = new Transaction("Loyer", rentAmount, TransactionType.EXPENSE);
            displayTransaction(rent);
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        BigDecimal salaryAmount = new BigDecimal("2500.00");

        Transaction salary = new Transaction("Salaire", salaryAmount, TransactionType.INCOME);

        displayTransaction(salary);
    }

    public static void displayTransaction(Transaction transaction) {
        System.out.println(
                "Description : " + transaction.getDescription()
                        + "\nMontant : " + transaction.getAmount()
                        + "\nType : " + transaction.getType()
        );
    }
}