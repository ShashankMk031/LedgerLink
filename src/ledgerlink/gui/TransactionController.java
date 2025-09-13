package ledgerlink.gui;

import java.util.stream.Collectors;
import ledgerlink.service.TransactionService;
import ledgerlink.model.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;

public class TransactionController {
    private final TransactionService transactionService = new TransactionService();

    @FXML
    private TextField accountIdField;
    
    @FXML
    private TextField pageField;
    
    @FXML
    private TextField pageSizeField;
    
    @FXML
    private TextArea resultArea;

    @FXML
    public void handleViewTransaction(ActionEvent event) {
        try {
            int accountId = Integer.parseInt(accountIdField.getText());
            int page = Integer.parseInt(pageField.getText());
            int size = Integer.parseInt(pageSizeField.getText());
            var lines = transactionService.listTransactionsByAccountSimple(accountId, (page - 1) * size, size);
            resultArea.setText(lines.stream()
                .map(Transaction::toString)
                .collect(Collectors.joining("\n")));
        } catch (NumberFormatException e) {
            resultArea.setText("Error: Please enter valid numbers for all fields");
        } catch (Exception e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }
}