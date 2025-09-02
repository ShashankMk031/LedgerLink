package ledgerlink.dao;

import ledgerlink.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Use the same Connection as money movement to keep it in one atomic TX
    public boolean insert(Connection conn, Transaction t) throws SQLException {
        String sql = "INSERT INTO transaction_ledger " +
                "(accountId, type, amount, createdAt, description, relatedAccountId) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getAccountId());
            ps.setString(2, t.getType());
            ps.setDouble(3, t.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(t.getCreatedAt()));
            ps.setString(5, t.getDescription());
            if (t.getRelatedAccountId() == null) ps.setNull(6, Types.INTEGER);
            else ps.setInt(6, t.getRelatedAccountId());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) t.setTransactionId(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public List<Transaction> findByAccountId(int accountId, Connection externalConn) throws SQLException {
        String sql = "SELECT transactionId, accountId, type, amount, createdAt, description, relatedAccountId " +
                     "FROM transaction_ledger WHERE accountId = ? ORDER BY createdAt DESC";
        boolean createdHere = false;
        Connection conn = externalConn;
        if (conn == null) {
            // Only for read convenience; writes should pass the existing TX connection
            conn = ledgerlink.util.DBUtil.getConnection();
            createdHere = true;
        }
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Transaction> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(map(rs));
                }
                return list;
            }
        } finally {
            if (createdHere && conn != null) conn.close();
        }
    }

    private Transaction map(ResultSet rs) throws SQLException {
        return new Transaction(
                rs.getInt("transactionId"),
                rs.getInt("accountId"),
                rs.getString("type"),
                rs.getDouble("amount"),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getString("description"),
                (Integer) rs.getObject("relatedAccountId")
        );
    }
}
