package ledgerlink.dao;


import ledgerlink.model.Loan; 
import ledgerlink.util.DBUtil; 

import java.sql.*; 
import java.time.LocalDateTime; 

public class LoanDAO { 

    public boolean insert(Loan l) throws SQLException { 
    String sql = "INSERT INTO loan (customerId, targetAccountId, principal, annualRate, termMonths, status, appliedAt) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // CHANGED: return keys
        ps.setInt(1, l.getCustomerId());
        if (l.getTargetAccountId() == null) ps.setNull(2, Types.INTEGER);
        else ps.setInt(2, l.getTargetAccountId());
        ps.setDouble(3, l.getPrincipal());
        ps.setDouble(4, l.getAnnualRate());
        ps.setInt(5, l.getTermMonths());
        ps.setString(6, l.getStatus());
        ps.setTimestamp(7, Timestamp.valueOf(l.getAppliedAt()));
        int rows = ps.executeUpdate(); 
        if(rows == 1) { 
            try (ResultSet keys = ps.getGeneratedKeys()) { 
                if (keys.next()) l.setLoanId(keys.getInt(1)); 
                } 
                return true; 
            } 
            return false;
        }
    } 

    public Loan findById(int loanId) throws SQLException { 
        String sql = "SELECT loanId, customerId, targetAccountId, principal, annualRate, termMonths, status, appliedAt, approvedAt, disbursedAt " + "FROM loan WHERE loanId = ? ";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loanId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
            return null;
        }
    } 


    public boolean updateStatusAndTime (int loanId, String status, String timeColumn, LocalDateTime time) throws SQLException {
        String sql = "UPDATE loan SET status = ? , " + timeColumn + " = ? WHERE loanId = ?"; 

        try (Connection conn = DBUtil.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) { 
                ps.setString(1, status); 
                ps.setTimestamp(2, time == null ? null : Timestamp.valueOf(time)); 
                ps.setInt(3, loanId); 
                return ps.executeUpdate() == 1; 
        }
    } 

    // Optional helpers for LoanService disburse safeguards
    public Loan findById(int loanId, Connection externalConn) throws SQLException {
    String sql = "SELECT loanId, customerId, targetAccountId, principal, annualRate, termMonths, status, appliedAt, approvedAt, disbursedAt " +
                 "FROM loan WHERE loanId = ?";
    boolean createdHere = false;
    Connection conn = externalConn;
    if (conn == null) { conn = DBUtil.getConnection(); createdHere = true; }
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, loanId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return map(rs);
            return null;
        }
    } finally {
        if (createdHere && conn != null) conn.close();
    }
    }

    public boolean markDisbursed(int loanId, int targetAccountId, Connection conn) throws SQLException {
    String sql = "UPDATE loan SET status = 'DISBURSED', disbursedAt = CURRENT_TIMESTAMP, targetAccountId = ? WHERE loanId = ? AND status = 'APPROVED'";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, targetAccountId);
        ps.setInt(2, loanId);
        return ps.executeUpdate() == 1;
    }
    }

    private Loan map(ResultSet rs) throws SQLException { 
        Timestamp a = rs.getTimestamp("appliedAt"); 
        Timestamp ap = rs.getTimestamp("approvedAt"); 
        Timestamp d = rs.getTimestamp("disbursedAt"); 
        return new Loan(
                rs.getInt("loanId"),
                rs.getInt("customerId"),
                (Integer) rs.getObject("targetAccountId"),
                rs.getDouble("principal"),
                rs.getDouble("annualRate"),
                rs.getInt("termMonths"),
                rs.getString("status"),
                a == null ? null : a.toLocalDateTime(),
                ap == null ? null : ap.toLocalDateTime(),
                d == null ? null : d.toLocalDateTime()
        );
    }
 }
