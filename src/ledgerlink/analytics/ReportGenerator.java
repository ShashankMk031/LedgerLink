package ledgerlink.analytics; 

import ledgerlink.dao.AccountDAO; 
import ledgerlink.dao.TransactionDAO;
import ledgerlink.model.Account;
import ledgerlink.model.Transaction; 

import java.io.FileWriter; 
import java.io.IOException; 
import java.sql.SQLException; 
import java.util.List; 

public class ReportGenerator { 

    private final AccountDAO accountDAO = new AccountDAO(); 
    private final TransactionDAO transactionDAO = new TransactionDAO(); 

    //Export all accounts to CSV 
    public void exportAccounts(String csvFile) throws IOException, SQLException {
         try (FileWriter fw = new FileWriter(csvFile, false)) {
            fw.write("accountId, customerId, currency, balance, status, branchId\n");
            List<Account> accounts = accountDAO.findByCustomerId(-1); 
            for (Account a:accounts) {
                fw.write(a.getAccountId() + "," + 
                        a.getCustomerId() + "," + 
                        a.getCurrency() + "," + 
                        a.getBalance() + "," + 
                        a.getStatus() + "," + 
                        a.getBranchId() + "\n");
            }
         }
    } 

    //Export all transactions for an account to CSV 
    public void exportTransactions(int accountId, String csvFile) throws IOException, SQLException {
        try (FileWriter fw = new FileWriter(csvFile, false)) {
            fw.write("transactionId, accountId, type, amount, createdAt, desctiption, relatedAccountId\n");
            List<Transaction> txs = transactionDAO.findByAccountId(accountId, null); 
            for (Transaction t:txs) {
                fw.write(t.getTransactionId() + "," + 
                        t.getAccountId() + "," + 
                        t.getType() + "," + 
                        t.getAmount() + "," + 
                        t.getCreatedAt() + "," + 
                        t.getDescription() + "," + 
                        t.getRelatedAccountId() + "\n");
            }
        }
    }
}