package ledgerlink.gui; 

import ledgerlink.model.Account; 
import ledgerlink.service.AccountService; 

// JavaFx 
import javafx.fxml.FXML; 
import javafx.scene.control.*;

import java.awt.TextField;

import javafx.event.ActionEvent; 

public class AccountController { 
    
    private final AccountService accountService = new AccountService(); 

    @FXML private TextField customerIdField; 
    @FXML private TextField currencyField; 
    @FXML private TextField depositField; 
    @FXML private TextField withdrawField; 
    @FXML private TextField transferToField; 
    @FXML private TextField transferAmountField;
    @FXML private TextField accountIdField; 
    @FXML private Label statusLabel; 
    
    //Called when "Open Account" is clicked 
    @FXML 
    public void handleOpenAccount(ActionEvent event) { 
        int customerId = Integer.parseInt(customerIdField.getText());
        String currency = currencyField.getText();
        boolean ok = accountService.openAccount(customerId, currency, 0.0, null); 
        statusLabel.setText(ok ? "Account opened" : "Error opening account."); 

    } 

    //"Deposit" click 
    @FXML 
    public void handleDeposit(ActionEvent event) { 
        int accountId = Integer.parseInt.parseInt(accountIdField.getText()); 
        double amount = Double.parseDouble(depositField.getText()); 
        boolean ok = accountService.deposit(accountId, amount); 
        statusLabel.setText(ok ? "Deposit successful" : "Error depositing.");
    } 

    // "Withdraw" click
    @FXML 
    public void handleWithdraw(ActionEvent event) { 
        int accountId = Integer.parseInt(accountIdField.getText()); 
        double amount = Double.parseDouble(withdrawField.getText()); 
        boolean ok = accountService.withdraw(accountId, amount); 
        statusLabel.setText(ok ? "Withdrawal successful" : "Error withdrawing.");
    } 

    //"Transfer" clciked 
    @FXML 
    public void handleTransfer(ActionEvent event) { 
        int fromId = Integer.parseInt(accountIdField.getText()); 
        int toId = Integer.pareInt(transferToField.getText()); 
        double amount = Double.parseDouble(transferAmountField.getText()); 
        boolean ok = accountService.transfer(fromId, toId, amount); 
        statusLabel.setText(ok ? "Transfer successful" : "Error transferring."); 
    } 

    //Close accoutn clicked 
    @FXML 
    public void handleCloseAccount(ActionEvent event) { 
        int accountId = Integer.parseInt(accountIdField.getText()); 
        boolean ok = accountService.closeAccount(accountId); 
        statusLabel.setText(ok ? "Account closed" : "Error closing account."); 
    }
}