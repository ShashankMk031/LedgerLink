package ledgerlink;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {

    // Load environment variables from .env file
    private static final Dotenv dotenv = Dotenv.load();

    // Read configuration from environment variables
    private static final String URL = dotenv.get("LEDGERLINK_DB_URL");
    private static final String USER = dotenv.get("LEDGERLINK_DB_USER");
    private static final String PASSWORD = dotenv.get("LEDGERLINK_DB_PASSWORD");

    /**
     * Ensures a required environment variable is present and not blank.
     * @param key Environment variable key
     * @param value Environment variable value
     * @return The non-null, non-blank value
     * @throws IllegalStateException if variable is missing or blank
     */
    private static String requireEnv(String key, String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }

    /**
     * Gets a connection to the database using credentials from environment variables.
     * Throws IllegalStateException if required variables are missing.
     * Attempts no-auth connection if user and password are not provided.
     * @return JDBC Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        String url = requireEnv("LEDGERLINK_DB_URL", URL);
        if ((USER == null || USER.isBlank()) && (PASSWORD == null || PASSWORD.isBlank())) {
            // Connect without username/password if both are missing or blank
            return DriverManager.getConnection(url);
        } else {
            // Connect with credentials
            String user = requireEnv("LEDGERLINK_DB_USER", USER);
            String pass = requireEnv("LEDGERLINK_DB_PASSWORD", PASSWORD);
            return DriverManager.getConnection(url, user, pass);
        }
    }

    /**
     * Closes the given JDBC connection quietly.
     * @param conn The Connection to close
     */
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
