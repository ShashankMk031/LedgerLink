package ledgerlink.model; 

public class Account { 
    private int accountId; 
    private int customerId; 
    private String currency; // eg. INR, USD, EUR 
    private double balance; 
    private String status ; // eg. Active, Inactive, Closed , Frozen 
    private Integer branchId;  // nullable for online accounts 

    public Account () {}   

    public Account (int accountId, int customerId, String currency, double balance, String status, Integer branchid) {
        this.accountId = accountId; 
        this.customerId = customerId; 
        this.currency = currency; 
        this.balance = balance; 
        this.status = status; 
        this.branchId = branchid;
    } 
    public int getAccountId() { return accountId; } 
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public int getCustomerId() { return customerId; } 
    public void setCustomerId(int customerId) { this.customerId = customerId; } 

    public String getCurrency() { return currency; } 
    public void setCurrency(String currency) { this.currency = currency; } 

    public double getBalance() { return balance; } 
    public void setBalance(double balance) { this.balance = balance; } 

    public String getStatus() { return status; } 
    public void setStatus(String status) { this.status = status; } 

    public Integer getBranchId() { return branchId; } 
    public void setBranchId(Integer branchId) { this.branchId = branchId; }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", customerId=" + customerId +
                ", currency='" + currency + '\'' +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                ", branchId=" + branchId +
                '}';
    }
}
