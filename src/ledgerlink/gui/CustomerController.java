package ledgerlink.gui; 

import ledgerlink.model.Customer; 
import ledgerlink.service.CustomerService; 

import javafx.fxml.FXML; 
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent; 

public class CustomerController { 
    private final CustomerService customerService = new CustomerService(); 

    @FXML private TextField nameField; 
    @FXML private TextField emailField; 
    @FXML private TextField phonTextField; 
    @FXML private TextField customerIdField; 
    @FXML private Label statusLabel;

    @FXML 
    public void handleAddCustomer(ActionEvent event) { 
        String name = nameField.getText();
        String email = emailField.getText(); 
        String phone = phonTextField.getText();
        Customer c = new Customer(0, name, email, phone); 
        boolean ok = customerService.addCustomer(c);
        statusLabel.setText(ok ? "Customer added" : "Error adding customer.");
    }

    @FXML 
    public void handleUpdateCustomer(ActionEvent event) { 
        int customerId = Integer.parseInt(customerIdField.getText()); 
        String name = nameField.getText();
        String email = emailField.getText(); 
        String phone = phonTextField.getText();
        Customer c = new Customer(customerId, name, email, phone); 
        boolean ok = customerService.updateCustomer(c);
        statusLabel.setText(ok ? "Customer updated" : "Error updating customer.");
    } 

    @FXML 
    public void handleDeleteCustomer(ActionEvent event) { 
        int customerId = Integer.parseInt(customerIdField.getText()); 
        boolean ok = customerService.deleteCustomer(customerId); 
        statusLabel.setText(ok ? "Customer deleted" : "Error deleting customer."); 
    }
    
    @FXML
    public void handleFindCustomer(ActionEvent event) {
        try {
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            Customer customer = customerService.getCustomerById(customerId);
            
            if (customer != null) {
                nameField.setText(customer.getName());
                emailField.setText(customer.getEmail());
                phonTextField.setText(customer.getPhone());
                statusLabel.setText("Customer found");
            } else {
                statusLabel.setText("Customer not found");
                // Clear fields if customer not found
                nameField.clear();
                emailField.clear();
                phonTextField.clear();
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid customer ID");
        } catch (Exception e) {
            statusLabel.setText("Error finding customer: " + e.getMessage());
            e.printStackTrace();
        }
    }
}