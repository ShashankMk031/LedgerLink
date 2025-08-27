package ledgerlink.service;  // Service layers sits b/w the DAO and the UI/Controller layer

/* 
 * DAO = raw DB operations (CRUD) 
 * Service = Business logic, validation, transaction management 
 * 
 * 
 * Uses DAO internally to perform DB operations
 */

import ledgerlink.model.Customer;
import ledgerlink.dao.CustomerDAO; 

import java.sql.SQLException;
import java.util.List;

public class CustomerService {

    private CustomerDAO customerDAO = new CustomerDAO(); 

    public boolean addCustomer(Customer customer) {
        try {
            return customerDAO.insert(customer);
        } catch (SQLException e) {
            System.err.println("Error adding customer: " + e.getMessage());
            return false;
        }
    } 

    public Customer getCustomerById(int customerId) {
        try { 
            return customerDAO.findById(customerId);
        } catch (SQLException e) {
            System.err.println("Error retrieving customer: " + e.getMessage());
            return null;
        }
    } 

    public List<Customer> getAllCustomers() {
        try {
            return customerDAO.findAll();
        } catch (SQLException e) {
            System.err.println("Error retrieving customers: " + e.getMessage());
            return List.of();  // Empty list on error
        }
    } 

    public boolean updateCustomer(Customer customer) {
        try {
            return customerDAO.update(customer);
        } catch (SQLException e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    } 

    public boolean deleteCustomer(int customerId) {
        try {
            return customerDAO.delete(customerId);
        } catch (SQLException e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }
}