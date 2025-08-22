package ledgerlink;

import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import java.util.Scanner; 

public class LedgerLinkApp {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in); 
        boolean running = true; 

        while(running){
            System.out.println("\n===== LedgerLink Banking Menu ====="); 
            System.out.println("1. View all customers");
            // FUTURE: Add more menu options here 
            System.out.println("2. Exit"); 
            System.out.println("Enter your choice: "); 
            int choice = scanner.nextInt();

            switch (choice){
                case 1:
                    viewAllCustomers();
                    break;
                // FUTURE: case 2: "View accounts" , case 3: "add customer", etc. 
                case 2:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
    /** 
     * Displays all customers with their details from the database 
     *  Connects using the DBUtils class and prints the result 
     *  */ 
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
        } finally{
            try{
                if(rs != null) rs.close() ; 
            } catch (Exception e){
                // Ignored 
            }
            try{
                if(stmt != null) stmt.close(); 
            } catch (Exception e){
                // Ignored 
            }
            DBUtils.closeConnection(conn);
        }
    }   


}