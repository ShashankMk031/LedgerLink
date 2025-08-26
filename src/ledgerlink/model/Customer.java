package ledgerlink.model;

import java.sql.Connection;
import java.sql.ResultSet; 
import java.sql.Statement;

import ledgerlink.util.DBUtils; 

public class Customer {
    // Prints all customer from the database 
    public static void viewAllCustomers(){
        Connection conn = null;
        Statement stmt = null; 
        ResultSet rs = null; 

        try{
            conn = DBUtils.getConnection(); 
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Customer");

            System.out.println("CustomerID | Name | Email | Phone | Address | CreatedAt"); 
            System.out.println("-------------------------------------------------------------");

            while (rs.next()){
                int id = rs.getInt("customer_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String createdAt = rs.getString("created_at");

                System.out.printf("%10d | %s | %s | %s | %s | %s\n", id, name, email, phone, address, createdAt);
            }

        } catch (Exception e){
            System.err.println("Error retrieving customers: " + e.getMessage());
        } finally {
            DBUtils.closeConnection(conn);
        }
    }
}