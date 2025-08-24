package ledgerlink;

import java.util.Scanner; 

public class LedgerLinkApp {

    public static void main(String[] args){ 
        Scanner scanner = new Scanner(System.in); 
        boolean running = true ; 

        while(running){
            System.out.println("\n ===== LedgerLink Menu =====");
            System.out.println("1. View All Customers");
            System.out.println("2. View Accounts by Customer ID");
            System.out.println("3.Exit");
            System.out.println("4.Deposit"); 
            System.out.println("5.Withdraw");
            System.out.println("Enter your choice: ");

            int choice; 
            if(scanner.hasNextInt()){
                choice = scanner.nextInt(); 
                scanner.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear invalid input
                continue;
            } 

            switch(choice){
                case 1: 
                    CustomerOperations.viewAllCustomers();
                    break;


                case 2:
                    System.out.print("Enter Customer ID to view accounts: ");
                    if(scanner.hasNextInt()){
                        int customerId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        AccountOperations.viewAccountsByCustomer(customerId);
                    } else {
                        System.out.println("Invalid Customer ID. Please enter a valid number.");
                        scanner.nextLine(); // Clear invalid input
                    }
                    break;


                case 3:
                    running = false;
                    break;


                case 4:
                    // Handles deposit input and call TransactionOperations.deposit() 
                    System.out.print("Enter Account ID for deposit: ");
                    int accountId;
                    if(scanner.hasNextInt()){
                        accountId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                    } else {
                        System.out.println("Invalid Account ID. Please enter a valid number.");
                        scanner.nextLine(); // Clear invalid input
                        break;
                    }
                    System.out.print("Enter amount to deposit: ");
                    double amount;
                    if(scanner.hasNextDouble()){
                        amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline
                    } else {
                        System.out.println("Invalid amount. Please enter a valid number.");
                        scanner.nextLine(); // Clear invalid input
                        break;
                    } 
                    System.out.print("Enter description (optional): ");
                    String description = scanner.nextLine();
                    TransactionOperations.deposit(accountId, amount, description);
                    break; 


                case 5: // Withdrawal 
                    System.out.print("Enter the Account ID for withdrawal:  ");
                    int withdrawAccountId; 
                    if (scanner.hasNextInt()) {
                        withdrawAccountId = scanner.nextInt();
                        scanner.nextLine();
                    } else {
                        System.out.println("Invalid Account ID. Please enter a valid number.");
                        scanner.nextLine(); // Clear invalid input
                        break;
                    }
                    System.out.println("Enter the amount to withdraw: ");
                    double withdrawAmount; 
                    if (scanner.hasNextDouble()) {
                        withdrawAmount = scanner.nextDouble();
                        scanner.nextLine();
                    } else {
                        System.out.println("Invalid amount. Please enter a valid number.");
                        scanner.nextLine(); // Clear invalid input
                        break;
                    }
                    System.out.println("Enter description (optional): ");
                    String withdrawDescription = scanner.nextLine();
                    TransactionOperations.withdraw(withdrawAccountId, withdrawAmount, withdrawDescription);
                    break;


                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }    
        }
        scanner.close();
    }
}
       