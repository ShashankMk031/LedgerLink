package ledgerlink.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ledgerlink.util.DBUtils;

public class Transaction {

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
    /** 
     * Transfer method 
     * @param fromAccountId The account to transfer from.
     * @param toAccountId The account to transfer to.
     * @param amount The amount to transfer.
     * @param description The transaction description.
     */
    
    public static void transfer(int fromAccountId ,int toAccountId, double amount, 
String description) {
        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        } 
        if (fromAccountId == toAccountId) {
            System.out.println("Cannot transfer to the same account.");
            return;
        }

        Connection conn = null; 
        PreparedStatement checkFromBalance = null ; 
        PreparedStatement updateFromBalance = null ;
        PreparedStatement updateToBalance = null ; 
        PreparedStatement insertTxnFrom = null ; 
        PreparedStatement insertTxnTo = null ; 
        ResultSet rs = null ; 

        try { 
            conn = DBUtils.getConnection(); 
            conn.setAutoCommit(false); 

            // Check balance on fromAccountId 
            checkFromBalance = conn.prepareStatement("SELECT balance FROM Account WHERE account_id = ?");
            checkFromBalance.setInt(1, fromAccountId);  // 1 -> is the first placeholder 
            rs = checkFromBalance.executeQuery(); 

            if (!rs.next()) {
                System.out.println("From Account ID not found.");
                conn.rollback(); 
                return;
            }
            double fromBalance = rs.getDouble("balance"); 
            if (amount > fromBalance) { 
                System.out.println("Insufficient funds for transfer.");
                conn.rollback(); 
                return;
            }

            // Check if toAccountId exists 
            try (PreparedStatement checkTo = conn.prepareStatement("SELECT 1 FROM Account WHERE account_id = ?")) { 
                checkTo.setInt(1, toAccountId) ; 
                ResultSet rs2 = checkTo.executeQuery(); 
                if (!rs2.next()) {
                    System.out.println("To Account ID not found.");
                    conn.rollback(); 
                    return;
                }
            } 

            updateFromBalance = conn.prepareStatement("UPDATE Account SET balance = balance - ? WHERE account_id = ? "); 
            updateFromBalance.setDouble(1, amount);
            updateFromBalance.setInt(2, fromAccountId);
            updateFromBalance.executeUpdate(); 

            // Add to destination account 
            updateToBalance = conn.prepareStatement("UPDATE Account SET balance = balance + ? WHERE acocunt_id = ? "); 
            updateToBalance.setDouble(1, amount); 
            updateToBalance.setInt(2, toAccountId); 
            updateToBalance.executeUpdate(); 

            // Inset transaction recored for source account ( transfer out) 
            insertTxnFrom = conn.prepareStatement("INSERT INTO Transaction (account_id, txn_type, amount, txn_time, description, related_account_id) VALUES (?, 'transfer_out', ? NOW(), ? , ?)"); 
            insertTxnFrom.setInt(1, fromAccountId); 
            insertTxnFrom.setDouble(2, amount);
            insertTxnFrom.setString(3, description);
            insertTxnFrom.setInt(4, toAccountId);
            insertTxnFrom.executeUpdate(); 

            // Insert transaction record for destination account ( transfer in) 
            insertTxnTo = conn.prepareStatement("INSERT INTO Transaction (account_id, txn_type, amount, txn_time, description, related_account_id) VALUES (? , 'transfer_in', ? , NOW(), ?, ?)"); 
            insertTxnTo.setInt(1, toAccountId); 
            insertTxnTo.setDouble(2, amount);
            insertTxnTo.setString(3, description);
            insertTxnTo.setInt(4, fromAccountId);
            insertTxnTo.executeUpdate(); 

            conn.commit(); 
            System.out.println("Transfer successful!"); 
        } catch (Exception e) { 
            System.err.println("Error processing transfer: " + e.getMessage()); 
            try { 
                if (conn != null) conn.rollback(); 
            } catch (Exception ex) { /* Ignored */ } 
        } finally { 
            try { if (rs != null) rs.close(); } catch (Exception e) {} 
            try { if (checkFromBalance != null) checkFromBalance.close(); } catch (Exception e) {} 
            try { if (updateFromBalance != null) updateFromBalance.close(); } catch (Exception e) {} 
            try { if (updateToBalance != null) updateToBalance.close(); } catch (Exception e) {} 
            try { if (insertTxnFrom != null) insertTxnFrom.close(); } catch (Exception e) {} 
            try { if (insertTxnTo != null) insertTxnTo.close(); } catch (Exception e) {} 
            DBUtils.closeConnection(conn);
        }
    }
}