package ledgerlink.service; 


import ledgerlink.dao.AccountDAO; 
import ledgerlink.dao.LoanDAO; 
import ledgerlink.dao.TransactionDAO;
import ledgerlink.model.Loan;
import ledgerlink.model.Transaction; 
import ledgerlink.util.DBUtil; 

import java.sql.Connection; 
import java.sql.SQLException; 
import java.time.LocalDateTime; 

public class LoanService { 
    
    private final LoanDAO loanDAO = new LoanDAO(); 
    private final AccountDAO accountDAO = new AccountDAO(); 
    private final TransactionDAO transactionDAO = new TransactionDAO(); 

    public Integer apply(int customerId, Integer targetAccountId, double principal, double annualRate, int termMonths) { 

        Loan l = new Loan(0, customerId, targetAccountId, principal, annualRate, termMonths,"PENDING", LocalDateTime.now(), null, null); 
        try { 
            boolean ok = loanDAO.insert(l); 
            return ok ? l.getLoanId() : null; 
        
        } catch (SQLException e) { 
            System.err.println("Loan apply failed: " + e.getMessage());
            return null;
        }
    } 

    public boolean approve(int loanId) { 
        try { 
            return loanDAO.updateStatusAndTime(loanId, "APPROVED", "approvedAt", LocalDateTime.now()); 

        } catch (SQLException e) { 
            System.err.println("Loan approval failed: " + e.getMessage());
            return false;
        }
    } 

    public boolean disburse(int loanId) { 
        try (Connection conn = DBUtil.getConnection()) { 
            conn.setAutoCommit(false); 
            Loan l = loanDAO.findById(loanId); 
            if (l == null || !"APPROVED".equals(l.getStatus()) || l.getTargetAccountId() == null){ 
                conn.rollback(); 
                return false; 
            } 

            // Credit the target account with the principat
            boolean credited = accountDAO.updateBalance(conn, l.getTargetAccountId(), l.getPrincipal()); 
            if (!credited) { conn.rollback(); return false; } 

            // Record transaction entry as CREDIt from loan 
            Transaction t = new Transaction(0, l.getTargetAccountId(),"LOAN_DISBURSAL",l.getPrincipal(), LocalDateTime.now(), "Loan disbursed loanId=" + l.getLoanId(), null); 

            if (!transactionDAO.insert(conn, t)) { conn.rollback(); return false; } 

            // Mark loan DISBURSED with time 
            boolean statusOk = loanDAO.updateStatusAndTime(loanId, "DISBURSED", "disbursedAt", LocalDateTime.now()); 
            if (!statusOk) { conn.rollback(); return false; } 

            conn.commit(); 
            return true; 
        } catch (SQLException e) { 
            System.err.println("Loan disbursement failed: " + e.getMessage());
            return false;
        } 
    }
}