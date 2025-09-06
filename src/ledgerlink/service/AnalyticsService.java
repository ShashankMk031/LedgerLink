package ledgerlink.service; 

import ledgerlink.dao.AccountDAO; 
import ledgerlink.dao.TransactionDAO; 
import ledgerlink.model.Account; 
import ledgerlink.model.Transaction; 

import java.sql.SQLException; 
import java.util.List; 

public class AnalyticsService { 
    
    private final AccountDAO accountDAO = new AccountDAO(); 
    private final TransactionDAO transactionDAO = new TransactionDAO(); 

    // Get total balances for all accounts 
    public double getTotalAssets() { 
        try { 
            List<Account>  accounts = accountDAO.findByCustomerId(-1);  // -1 wildcard - means fetch from all account or any customer 
            double total = 0; 
            for (Account a: accounts) total += a.getBalance(); 
            return total;

        } catch (SQLException e) { 
            System.err.println("Error computin total assets: " + e.getMessage()); 
            return 0.0;
        }
    } 

    // Get number of transactions for an account 
    public int getTransactionCount(int accountId) { 
        try { 
            List<Transaction> txs = transactionDAO.findByAccountId(accountId, null); 
            return txs.size(); 
        } catch (SQLException e) { 
            System.err.println("Error computing transaction count: " + e.getMessage()); 
            return 0;
        }
    } 

    // Get sum of all deposits for an account 
    public double getDepositTotal (int accountId) { 
        try { 
            List<Transaction> txs = transactionDAO.findByAccountId(accountId, null); 
            return txs.stream() // Stream -> a pipeline for processing
                    .filter(t -> "DEPOSIT".equalsIgnoreCase(t.getType()))  // For each transaction t check if its type equal DEPOSIT
                    .mapToDouble(Transaction::getAmount) // Take each "Transaction" and convert it to just its amount(double) 
                    .sum(); 

        } catch (SQLException e) { 
            System.err.println("Error while getting deposit total : " + e.getMessage()); 
            return 0.0;
        }
    }
}