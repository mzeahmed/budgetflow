import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Budget {

    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        if (transaction == null) {
            Objects.requireNonNull(transaction, "Transaction cannot be null");
        }

        transactions.add(transaction);
    }

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

    public List<Transaction> getTransactions() {
        return List.copyOf(transactions);
    }
}