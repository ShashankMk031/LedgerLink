package ledgerlink.service;

import ledgerlink.dao.AccountDAO;
import ledgerlink.model.Account;
import ledgerlink.util.AuditLogger;
import ledgerlink.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class AccountService {
    private final AccountDAO accountDAO = new AccountDAO();
    private final NotificationService notificationService = new NotificationService(); 
    private final FraudService fraudService = new FraudService(); 
    public boolean openAccount(int customerId, String currency, double initialDeposit, Integer branchId) {
        Account a = new Account(0, customerId, currency, initialDeposit, "ACTIVE", branchId);
        try {
            boolean ok = accountDAO.insert(a);
            if (ok) {
                AuditLogger.log("system", "OPEN_ACCOUNT", "Account", "accountId=" + a.getAccountId() + ", customerId=" + customerId);
            }
            return ok;
        } catch (SQLException e) {
            System.err.println("Error opening account: " + e.getMessage());
            return false;
        }
    }

    public Account getAccount(int accountId) {
        try {
            return accountDAO.findById(accountId);
        } catch (SQLException e) {
            System.err.println("Error fetching account: " + e.getMessage());
            return null;
        }
    }

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
            if (!ok) { conn.rollback(); return false; }
            conn.commit();

            // audit + notify after commit
            AuditLogger.log("system", "DEPOSIT", "Account", "accountId=" + accountId + ", amount=" + amount);
            notificationService.notifyEmail("noreply@example.com", "Deposit", "Account " + accountId + " credited " + amount);

            return true;
        } catch (SQLException e) {
            System.err.println("Deposit failed: " + e.getMessage());
            return false;
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
            if (!ok) { conn.rollback(); return false; }
            conn.commit();

            // audit + notify after commit
            AuditLogger.log("system", "WITHDRAW", "Account", "accountId=" + accountId + ", amount=" + amount);
            notificationService.notifyEmail("noreply@example.com", "Withdrawal", "Account " + accountId + " debited " + amount);

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
            if (from == null || to == null) { conn.rollback(); return false; }
            if (!"ACTIVE".equals(from.getStatus()) || !"ACTIVE".equals(to.getStatus())) { conn.rollback(); return false; }
            if (!from.getCurrency().equals(to.getCurrency())) { System.err.println("Transfer failed: Currency mismatch"); conn.rollback(); return false; }
            if (from.getBalance() < amount) { conn.rollback(); return false; }

            boolean debited = accountDAO.updateBalance(conn, fromAccountId, -amount);
            boolean credited = accountDAO.updateBalance(conn, toAccountId, amount);
            if (!(debited && credited)) {
                conn.rollback();
                return false;
            }

            conn.commit();

            // audit + notify after commit
            AuditLogger.log("system", "TRANSFER", "Account", "from=" + fromAccountId + ", to=" + toAccountId + ", amount=" + amount);
            notificationService.notifyEmail("noreply@example.com", "Transfer", "From " + fromAccountId + " to " + toAccountId + " amount " + amount);
            

            return true;
        } catch (SQLException e) {
            System.err.println("Transfer failed: " + e.getMessage());
            return false;
        }
    }

    public boolean closeAccount(int accountId) {
        try {
            Account a = accountDAO.findById(accountId);
            if (a == null || !"ACTIVE".equals(a.getStatus()) || a.getBalance() != 0) {
                return false; // Only ACTIVE accounts with zero balance can be closed
            }
            boolean ok = accountDAO.updateStatus(accountId, "CLOSED");

            if (ok) {
                AuditLogger.log("system", "CLOSE_ACCOUNT", "Account", "accountId=" + accountId);
            }

            return ok;
        } catch (SQLException e) {
            System.err.println("Error closing account: " + e.getMessage());
            return false;
        }
    }

}
