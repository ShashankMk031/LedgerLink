package ledgerlink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountOperations {

    /**
     * Displays all accounts for the given customer_id.
     *
     * @param customerId The customer ID whose accounts to display.
     */
    public static void viewAccountsByCustomer(int customerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            // Join Account with Branch to get branch_name and location along with account details
            String sql = "SELECT a.account_id, a.account_type, a.balance, a.opened_at, b.branch_name, b.location\n" + //
                                "FROM Account a\n" + //
                                "LEFT JOIN Branch b ON a.branch_id = b.branch_id\n" + //
                                "WHERE a.customer_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            rs = pstmt.executeQuery();

            System.out.println("AccountID | AccountType | Balance  | OpenedAt             | BranchName      | Location");
            System.out.println("-------------------------------------------------------------------------------------------");

            boolean hasAccounts = false;
            while (rs.next()) {
                hasAccounts = true;
                int accountId = rs.getInt("account_id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                String openedAt = rs.getString("opened_at");
                String branchName = rs.getString("branch_name");
                String location = rs.getString("location");

                System.out.printf("%9d | %11s | %8.2f | %20s | %15s | %s\n",
                        accountId, accountType, balance, openedAt, branchName, location);
            }

            if (!hasAccounts) {
                System.out.println("No accounts found for customer ID: " + customerId);
            }
        } catch (Exception e) {
            System.err.println("Error retrieving accounts: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
                // Ignored
            }
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                // Ignored
            }
            DBUtils.closeConnection(conn);
        }
    }
}
