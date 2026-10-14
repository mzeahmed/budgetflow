import java.math.BigDecimal;

public class Transaction {

    private String description;
    private BigDecimal amount;
    private TransactionType type;

    public Transaction(String description, BigDecimal amount, TransactionType type) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }
}