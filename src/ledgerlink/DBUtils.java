package ledgerlink;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = dotenv.get("LEDGERLINK_DB_URL");
    private static final String USER = dotenv.get("LEDGERLINK_DB_USER");
    private static final String PASSWORD = dotenv.get("LEDGERLINK_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
