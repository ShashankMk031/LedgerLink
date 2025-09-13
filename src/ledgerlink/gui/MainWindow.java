package ledgerlink.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class MainWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Print current working directory and classpath for debugging
            System.out.println("Working Directory: " + System.getProperty("user.dir"));
            System.out.println("Classpath: " + System.getProperty("java.class.path"));
            
            // Try loading FXML with relative path first
            URL fxmlUrl = getClass().getResource("main_window.fxml");
            if (fxmlUrl == null) {
                // If not found, try with absolute path
                fxmlUrl = getClass().getResource("/ledgerlink/gui/main_window.fxml");
                if (fxmlUrl == null) {
                    throw new IOException("Cannot find FXML file: main_window.fxml. Tried both relative and absolute paths.");
                }
            }
            
            System.out.println("Loading FXML from: " + fxmlUrl);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(fxmlUrl);
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 800, 600);
            
            primaryStage.setTitle("LedgerLink");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args) {
        try {
            // Set system properties for JavaFX
            System.setProperty("javafx.preloader", "none");
            
            // Print classpath for debugging
            System.out.println("Classpath: " + System.getProperty("java.class.path"));
            
            // Launch the JavaFX application
            launch(args);
        } catch (Exception e) {
            System.err.println("Application Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}