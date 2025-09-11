package ledgerlink.gui; 

import java.awt.Label;
import java.awt.event.ActionEvent;
import ledgerlink.model.Customer; 
import ledgerlink.service.CustomerService; 

import javafx.fxml.FXML; 
import java.scene.control.*; 
import java.event.ActionEvent; 

public class CustomerController{ 
    private final CustomerService customerService = new CustomerService(); 

    @FXML private TextField nameField; 
    @FXML private TextField emailField; 
    @FXML private TextField phonTextField ; 
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
}