package ledgerlink.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage; 

public class MainWindow extends Application { 
    @Override 
    public void start(Stage primaryStage) throws Exception { 
        // Load an FXML file (main_window.fxml) and create a new Scene , and should have tabbed panes or buttons for controller screens 
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_window.fxml")); 
        Scene scene = new Scene(loader.load()); 
        primaryStage.setTitle("LedgerLink"); 
        primaryStage.setScene(scene); 
        primaryStage.show();
    } 

    public static void main(String[] args) { 
        launch(args); // Launch javaFx app 
    }
}