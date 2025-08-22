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
            System.out.println("2. View accounts by customer");
            System.out.println("3. Exit"); 
            System.out.print("Enter your choice: ");
            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume trailing newline
            } else {
                System.out.println("Please enter a number (e.g., 1 or 2).");
                scanner.nextLine(); // discard invalid input
                continue; // prompt again
            }

            switch (choice){
                case 1:
                    CustomerOperations.viewAllCustomers();
                    break;
                case 2:
                    System.out.println("Enter the Customer ID to view accounts: ");
                    if (scanner.hasNextInt()){
                        int customerId = scanner.nextInt();
                        AccountOperations.viewAccountsByCustomer(customerId);
                    } else{
                        System.out.println("Invalid Customer ID , Please enter a valid numeric ID.");
                        scanner.next();
                    }
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
} 