package ledgerlink.util; 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv; 

public class DBUtils { 
    
    private static Dotenv dotenv = Dotenv.load(); 

    private static final Sting URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD"); 

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load JDBC driver or MySQL JDBC Driver not found.");

        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Utility method to close connection 
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close(); 
            } catch (SQLException e) {
                System.err.println("Failed to close connection.");
            }
        }
    }
}