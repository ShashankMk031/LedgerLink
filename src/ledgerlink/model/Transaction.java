package ledgerlink.model; 

import java.time.LocalDateTime; 

public class Transaction { 
    private int transactionId; 
    private int accountId; 
    private String type;  // " depoist, withdrawal, transfer "
    private double amount; 
    private LocalDateTime createdAt;
    private String description; 
    private Integer relatedAccountId; // for transfers , null otherwise 

    public Transaction () {} 

public Transaction(int transactionId, int accountId, String type, double amount,
                   LocalDateTime createdAt, String description, Integer relatedAccountId) { // CHANGED param name
    this.transactionId = transactionId;
    this.accountId = accountId;
    this.type = type;
    this.amount = amount;
    this.createdAt = createdAt;
    this.description = description; 
    this.relatedAccountId = relatedAccountId;
}
    public int getTransactionId() { return transactionId; } 
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getAccountId() { return accountId; } 
    public void setAccountId(int accountId) { this.accountId = accountId; } 

    public String getType() { return type; } 
    public void setType(String type) { this.type = type; } 

    public double getAmount() { return amount; } 
    public void setAmount(double amount) { this.amount = amount; } 

    public LocalDateTime getCreatedAt() { return createdAt; } 
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; } 

    public String getDescription() { return description; } 
    public void setDescription(String description) { this.description = description; } 

    public Integer getRelatedAccountId() { return relatedAccountId; } 
    public void setRelatedAccountId(Integer relatedAccountId) { this.relatedAccountId = relatedAccountId; } 

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountId=" + accountId +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", relatedAccountId=" + relatedAccountId +
                '}';
    }

}