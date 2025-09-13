package ledgerlink.gui;

import ledgerlink.service.LoanService;
import javafx.fxml.FXML; 
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class LoanController { 
    private final LoanService loanService = new LoanService();

    @FXML private TextField customerIdField; 
    @FXML private TextField targetAccountIdField; 
    @FXML private TextField principalField; 
    @FXML private TextField rateField; 
    @FXML private TextField termField; 
    @FXML private TextField loanIdField; 
    @FXML private Label statusLabel; 

    @FXML 
    public void handleApplyLoan(ActionEvent event) { 
        int customerId = Integer.parseInt(customerIdField.getText());
        int targetAccountId = Integer.parseInt(targetAccountIdField.getText()); 
        double principle = Double.parseDouble(principalField.getText()); 
        double rate = Double.parseDouble(rateField.getText()); 
        int term = Integer.parseInt(termField.getText()); 
        Integer loanId = loanService.apply(customerId, targetAccountId, principle, rate, term);
        statusLabel.setText(loanId != null ? "Loan applied with id " + loanId : "Error applying loan.");
    } 

    @FXML
    public void handleApproveLoan(ActionEvent event) {
        int loanId = Integer.parseInt(loanIdField.getText()); 
        boolean ok = loanService.approve(loanId); 
        statusLabel.setText(ok ? "Loan approved" : "Error approving loan."); 
    } 

    @FXML 
    public void handleDisburseLoan(ActionEvent event) { 
        int loanId = Integer.parseInt(loanIdField.getText()); 
        boolean ok = loanService.disburse(loanId); 
        statusLabel.setText(ok ? "Loan disbursed" : "Error disbursing loan."); 
    }
}