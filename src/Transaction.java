import java.math.BigDecimal;

public class Transaction {

    private final String description;
    private final BigDecimal amount;
    private final TransactionType type;

    public Transaction(String description, BigDecimal amount, TransactionType type) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        if (type == null) {
            throw new IllegalArgumentException("Type must not be null");
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