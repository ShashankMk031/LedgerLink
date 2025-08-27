package ledgerlink.dao;    // DAO - Data Access Object for Customer entity , Seperates DB operations from business logic

import ledgerlink.model.Customer ; 
import ledgerlink.util.DBUtil ; 

import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.util.ArrayList; 
import java.util.List;   

public class CustomerDAO { 

    // insert a new customer into the database 
    public boolean insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (name, email, phone) VALUES (?, ?, ?)";

        try(Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getEmail());
                ps.setString(3, customer.getPhone());
                return ps.executeUpdate() == 1; 
        }
    } 

    // Find customer by ID 
    public Customer findById(int customerId) throws SQLException {
        String sql= "SELECT customer_id, name, email, phone FROM Customer WHERE customer_id = ?";
        try(Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1, customerId);
                try(ResultSet rs = ps.executeQuery()){
                    if(rs.next()){
                        return new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("phone")
                        );
                    } else {
                        return null; 
                    }
                }
        }
    }

    // Retrieve all customers 
    public List<Customer> findAll() throws SQLException {
        String sql = "SELECT customer_id, name, email, phone FROM Customer"; 
        List<Customer> customers = new ArrayList<>();
        try (Connection conn DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    customers.add(new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                    ));
                        }
        }
        return customers; 
    }

    // Update an existing customer 
    public boolean update (Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET name = ? , email = ?, phone = ? WHERE customer_id = ?";
        try (Connection conn = DBUtils.getConnection(); 
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getEmail());
                ps.setString(3, customer.getPhone());
                ps.setInt(4, customer.getCustomerId());
                return ps.executeUpdate() == 1;
            }
    }

    // Delete a customer by ID
    public boolean delete(int customerId) throws SQLException {
        String sql = "DELETE FROM Customer WHERE customer_id = ?";
        try (Connection conn = DBUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, customerId);
                return ps.executeUpdate() == 1;
            }
    }

}