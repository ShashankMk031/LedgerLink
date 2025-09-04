/* Apply: create a loan request tied to a customer and a target account (where funds will be disbursed) with principal, rate, term, and status=PENDING.

Approve: set status=APPROVED with an approval timestamp.

Disburse: move funds into the borrower account in a single DB transaction and set status=DISBURSED.

 */ 


// ensuress minimal fields 

package ledgerlink.model; 

import java.time.LocalDateTime; 

public class Loan { 
    private int loanId; 
    private int customerId; 
    private Integer targetAccountId; 
    private double principal ; 
    private double annualRate; 
    private int termMonths; 
    private String status;   // Pending, Approved, disbursed , Rejected, Closed 
    private LocalDateTime appliedAt; 
    private LocalDateTime approvedAt; 
    private LocalDateTime disbursedAt; 
    

    public Loan() {} 

    public Loan(int loadId, int customerId, Integer targetAccountId, double principal, double annualRate , int termMonths, String status, LocalDateTime appliedAt, LocalDateTime approvedAt, LocalDateTime disbursedAt) {
        this.loanId = loadId; 
        this.customerId = customerId; 
        this.targetAccountId = targetAccountId; 
        this.principal = principal; 
        this.annualRate = annualRate; 
        this.termMonths = termMonths; 
        this.status = status; 
        this.appliedAt = appliedAt; 
        this.approvedAt = approvedAt; 
        this.disbursedAt = disbursedAt;
    } 

    public int getLoanId() { return loanId; } 
    public void setLoanId(int loanId) { this.loanId = loanId; } 

    public int getCustomerId() { return customerId; } 
    public void setCustomerId(int customerId) { this.customerId = customerId; } 

    public Integer getTargetAccountId() { return targetAccountId; } 
    public void setTargetAccountId(Integer targetAccountId) { this.targetAccountId = targetAccountId; } 

    public double getPrincipal() { return principal; } 
    public void setPrincipal(double principal) { this.principal = principal; } 

    public double getAnnualRate() { return annualRate; } 
    public void setAnnualRate(double annualRate) { this.annualRate = annualRate; } 

    public int getTermMonths() { return termMonths; } 
    public void setTermMonths(int termMonths) { this.termMonths = termMonths; } 

    public String getStatus() { return status; } 
    public void setStatus(String status) { this.status = status; } 

    public LocalDateTime getAppliedAt() { return appliedAt; } 
    public void setAppliedAt(LocalDateTime appliedAt) { this.appliedAt = appliedAt; } 

    public LocalDateTime getApprovedAt() { return approvedAt; } 
    public void setApprovedAt(LocalDateTime approvedAt) { this.approvedAt = approvedAt; } 

    public LocalDateTime getDisbursedAt() { return disbursedAt; }   
    public void setDisbursedAt(LocalDateTime disbursedAt) { this.disbursedAt = disbursedAt; }
} 