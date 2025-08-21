package ledgerlink;

import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.Statement; 

public class LedgerLinkApp { 
    public static void main(String[] args){
        System.out.println("=== LedgerLink : View Customers ==="); 
        viewAllCustomers(); 
    }

    public static void viewAllCustomers() { 
        Connection conn = null; 
        Statement stmt = null ; 
        ResultSet rs = null; 

        try{
            conn = DBUtils.getConnection(); 
            stmt = conn.createStatement(); 
            rs = stmt.executeQuery("SELECT * FROM Customer"); 
            System.out.println("Customer ID | Name | Email | Phone | Address | Created_At");
            System.out.println("----------------------------------------------------------"); 

            while (rs.next()) { 
                int id = rs.getInt("customer_id"); 
                String name = rs.getString("name"); 
                String email = rs.getString("email"); 
                String phone = rs.getString("phone"); 
                String address = rs.getString("address"); 
                String createdAt = rs.getString("created_at"); 

                System.out.printf("%10d | %s | %s | %s | %s | %s%n", 
                                  id, name, email, phone, address, createdAt);
            }
        } catch (Exception e) { 
            System.err.println("Error retreving customers" + e.getMessage()); 
        } finally { 
            try {
                if (rs!= null) rs.close(); }
            catch (Exception e) { /* ignore   */
            }
            try {
                if (stmt != null) stmt.close(); 
            } catch (Exception e) { /* ignore */ 
            }
            DBUtils.closeConnection(conn);
        }
    }
}