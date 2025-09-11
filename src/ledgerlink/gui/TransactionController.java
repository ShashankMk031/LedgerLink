package ledgerlink.gui;

import java.awt.TextField;
import java.awt.event.ActionEvent;
import ledgerlink.service.TransactionService;
import javafx.fxml.FXML; 
import javafx.scene.control.*; 
import javafx.event.ActionEvent; 

public class TransactionController {
    private final TransactionService transactionService = new TransactionService(); 

    @FXML private TextField accountIdField;
    @FXML private TextField pageField; 
    @FXML private TextField pageSizeField; 
    @FXML private TextField resultArea; 

    @FXML 
    public void handleViewTransaction(ActionEvent event) { 
        int accountId = Integer.parseInt(accountIdField.getText()); 
        int page = Integer.parseInt(pageField.getText()); 
        int size = Integer.parseInt(pageSizeField.getText()); 
        var lines = transactionService.listTransactionsByAccountSimple(accountId, (page - 1) * size, size);
        resultArea.setText(String.join("\n", lines));
    }
}