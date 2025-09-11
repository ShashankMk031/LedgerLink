package ledgerlink.service;

import ledgerlink.dao.AccountDAO;
import ledgerlink.dao.TransactionDAO;
import ledgerlink.model.Account;
import ledgerlink.model.Transaction;
import ledgerlink.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionService {
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final AccountDAO accountDAO = new AccountDAO(); // Add this line

    public boolean deposit(int accountId, double amount) {
        if (amount <= 0) return false;
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            Account acc = accountDAO.findById(accountId);
            if (acc == null || !"ACTIVE".equals(acc.getStatus())) {
                conn.rollback();
                return false;
            }

            boolean ok = accountDAO.updateBalance(conn, accountId, amount);
            if (!ok) { 
                conn.rollback(); 
                return false; 
            }
            Transaction t = new Transaction(0, accountId, "DEPOSIT", amount,
                    LocalDateTime.now(), "Cash deposit", null);
            if (!transactionDAO.insert(conn, t)) { 
                conn.rollback(); 
                return false; 
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Deposit failed: " + e.getMessage());
            return false;
        }
    }

    public List<Transaction> listTransactionsByAccountSimple(int accountId, int offset, int limit) {
        try (Connection conn = DBUtil.getConnection()) {
            return transactionDAO.findByAccountId(accountId, offset, limit, null);
        } catch (SQLException e) {
            System.err.println("Error retrieving transactions: " + e.getMessage());
            return List.of();
        }
    }

    public boolean withdraw(int accountId, double amount) {
        if (amount <= 0) return false;
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);
            Account acc = accountDAO.findById(accountId);
            if (acc == null || !"ACTIVE".equals(acc.getStatus()) || acc.getBalance() < amount) {
                conn.rollback();
                return false;
            }

            boolean ok = accountDAO.updateBalance(conn, accountId, -amount);
            if (!ok) { 
                conn.rollback(); 
                return false; 
            }
            Transaction t = new Transaction(0, accountId, "WITHDRAW", amount,
                    LocalDateTime.now(), "Cash withdrawal", null);
            if (!transactionDAO.insert(conn, t)) { 
                conn.rollback(); 
                return false; 
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Withdraw failed: " + e.getMessage());
            return false;
        }
    }

    public boolean transfer(int fromAccountId, int toAccountId, double amount) {
        if (amount <= 0 || fromAccountId == toAccountId) return false;
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false);

            Account from = accountDAO.findById(fromAccountId);
            Account to = accountDAO.findById(toAccountId);
            if (from == null || to == null) { 
                conn.rollback(); 
                return false; 
            }
            if (!"ACTIVE".equals(from.getStatus()) || !"ACTIVE".equals(to.getStatus())) { 
                conn.rollback(); 
                return false; 
            }

            if (!from.getCurrency().equals(to.getCurrency())) {
                System.err.println("Transfer failed: Currency mismatch");
                conn.rollback();
                return false;
            }

            if (from.getBalance() < amount) { 
                conn.rollback(); 
                return false; 
            }

            boolean debited = accountDAO.updateBalance(conn, fromAccountId, -amount);
            boolean credited = accountDAO.updateBalance(conn, toAccountId, amount);
            if (!(debited && credited)) { 
                conn.rollback(); 
                return false; 
            }
            Transaction t1 = new Transaction(0, fromAccountId, "TRANSFER", amount,
                    LocalDateTime.now(), "Transfer to " + toAccountId, toAccountId);
            Transaction t2 = new Transaction(0, toAccountId, "TRANSFER", amount,
                    LocalDateTime.now(), "Transfer from " + fromAccountId, fromAccountId);
            if (!transactionDAO.insert(conn, t1) || !transactionDAO.insert(conn, t2)) { 
                conn.rollback(); 
                return false; 
            }
            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Transfer failed: " + e.getMessage());
            return false;
        }
    }
}