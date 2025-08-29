package ledgerlink.dao;

import ledgerlink.model.Account;
import ledgerlink.util.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public boolean insert(Account a) throws SQLException {
        String sql = "INSERT INTO account (customerid, currency, balance, status, branchId) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, a.getCustomerId());
            ps.setString(2, a.getCurrency());
            ps.setDouble(3, a.getBalance());
            ps.setString(4, a.getStatus());
            if (a.getBranchId() == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, a.getBranchId());
            int rows = ps.executeUpdate();
            if (rows == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) a.setAccountId(keys.getInt(1));
                }
                return true;
            }
            return false;
        }
    }

    public Account findById(int accountId) throws SQLException {
        String sql = "SELECT accountId, customerId, currency, balance, status, branchId FROM account WHERE accountId = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
            return null;
        }
    }

    public List<Account> findByCustomerId(int customerId) throws SQLException {
        String sql = "SELECT accountId, customerId, currency, balance, status, branchId FROM account WHERE customerId = ?";
        List<Account> list = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean updateStatus(int accountId, String status) throws SQLException {
        String sql = "UPDATE account SET status = ? WHERE accountId = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, accountId);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean updateBalance(Connection conn, int accountId, double delta) throws SQLException {
        String sql = "UPDATE account SET balance = balance + ? WHERE accountId = ? AND status = 'ACTIVE'";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, delta);
            ps.setInt(2, accountId);
            return ps.executeUpdate() == 1;
        }
    }

    private Account map(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("accountId"),
                rs.getInt("customerId"),
                rs.getString("currency"),
                rs.getDouble("balance"),
                rs.getString("status"),
                (Integer) rs.getObject("branchId")
        );
        // Uses JDBC mapping pattern with getters per column [3][5].
    }
}
