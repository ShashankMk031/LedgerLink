package ledgerlink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionOperations {

    /**
     * Deposit a specified amount into an account.
     * @param accountId The account to deposit into.
     * @param amount The amount to deposit.
     * @param description The transaction description.
     */
    public static void deposit(int accountId, double amount, String description) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        Connection conn = null;
        PreparedStatement updateBalance = null;
        PreparedStatement insertTxn = null;

        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false); // for transaction safety

            // Update account balance
            updateBalance = conn.prepareStatement(
                "UPDATE Account SET balance = balance + ? WHERE account_id = ?"
            );
            updateBalance.setDouble(1, amount);
            updateBalance.setInt(2, accountId);
            int rows = updateBalance.executeUpdate();

            if (rows == 0) {
                System.out.println("Account ID not found.");
                conn.rollback();
                return;
            }

            // Insert transaction record
            insertTxn = conn.prepareStatement(
                "INSERT INTO Transaction (account_id, txn_type, amount, txn_time, description, related_account_id) VALUES (?, 'deposit', ?, NOW(), ?, NULL)"
            );
            insertTxn.setInt(1, accountId);
            insertTxn.setDouble(2, amount);
            insertTxn.setString(3, description);
            insertTxn.executeUpdate();

            conn.commit();
            System.out.println("Deposit successful!");
        } catch (Exception e) {
            System.err.println("Error processing deposit: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) { /* Ignored */ }
        } finally {
            try { if (updateBalance != null) updateBalance.close(); } catch (Exception e) {}
            try { if (insertTxn != null) insertTxn.close(); } catch (Exception e) {}
            DBUtils.closeConnection(conn);
        }
    }

    // NEW: Withdrawal operation
    /**
     * Withdraw a specified amount from an account.
     * @param accountId The account to withdraw from.
     * @param amount The amount to withdraw.
     * @param description The transaction description.
     */
    public static void withdraw(int accountId, double amount, String description) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }
        Connection conn = null;
        PreparedStatement checkBalance = null;
        PreparedStatement updateBalance = null;
        PreparedStatement insertTxn = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false); // for transaction safety

            // 1. Check current balance
            checkBalance = conn.prepareStatement(
                "SELECT balance FROM Account WHERE account_id = ?"
            );
            checkBalance.setInt(1, accountId);
            rs = checkBalance.executeQuery();

            if (!rs.next()) {
                System.out.println("Account ID not found.");
                return;
            }

            double currentBalance = rs.getDouble("balance");
            if (amount > currentBalance) {
                System.out.println("Insufficient funds for withdrawal.");
                return;
            }

            // 2. Update account balance
            updateBalance = conn.prepareStatement(
                "UPDATE Account SET balance = balance - ? WHERE account_id = ?"
            );
            updateBalance.setDouble(1, amount);
            updateBalance.setInt(2, accountId);
            updateBalance.executeUpdate();

            // 3. Insert transaction record
            insertTxn = conn.prepareStatement(
                "INSERT INTO Transaction (account_id, txn_type, amount, txn_time, description, related_account_id) VALUES (?, 'withdrawal', ?, NOW(), ?, NULL)"
            );
            insertTxn.setInt(1, accountId);
            insertTxn.setDouble(2, amount);
            insertTxn.setString(3, description);
            insertTxn.executeUpdate();

            conn.commit();
            System.out.println("Withdrawal successful!");
        } catch (Exception e) {
            System.err.println("Error processing withdrawal: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
            } catch (Exception ex) { /* Ignored */ }
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (checkBalance != null) checkBalance.close(); } catch (Exception e) {}
            try { if (updateBalance != null) updateBalance.close(); } catch (Exception e) {}
            try { if (insertTxn != null) insertTxn.close(); } catch (Exception e) {}
            DBUtils.closeConnection(conn);
        }
    }
    // Next: Transfer method can be added here!
}
