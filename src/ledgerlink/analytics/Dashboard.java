package ledgerlink.analytics ; 

import ledgerlink.dao.AccountDAO;
import ledgerlink.dao.TransactionDAO;
import ledgerlink.model.Account;
import ledgerlink.model.Transaction; 

import java.sql.SQLException; 
import java.util.List; 

public class Dashboard { 

    private final AccountDAO accountDAO = new AccountDAO(); 
    private final TransactionDAO transactionDAO = new TransactionDAO(); 

    public int getTotalAccounts() { 
        try { 
            List<Account> all = accountDAO.findByCustomerId(-1); 
            return all.size();
        } catch (SQLException e) { 
            System.err.println("Error computing total accounts: " + e.getMessage()); 
            return 0;
        } 
    } 


    public double getTotalDeposits() { 
        try { 
            List<Account> all = accountDAO.findByCustomerId(-1); 
            double total = 0.0; 
            for (Account a: all) total += a.getBalance(); 
            return total ;
        } catch (SQLException e) { 
            System.err.println("Error computing total assets: " + e.getMessage()); 
            return 0.0;
        }
    } 

    public int getTotalTransaction() { 
        try { 
            int total = 0 ; 
            List<Account> all = accountDAO.findByCustomerId(-1); 
            for (Account a : all) {
            List <Transaction> txs = transactionDAO.findByAccountId(a.getAccountId(), null);
            total += txs.size();   
            }  
            return total;
        } catch (SQLException e) { 
            System.err.println("Error computing total assets: " + e.getMessage()); 
            return 0;
        }
    }
}