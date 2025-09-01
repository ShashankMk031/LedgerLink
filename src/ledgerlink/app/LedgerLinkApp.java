package ledgerlink.app; 

import ledgerlink.service.CustomerService; 
import ledgerlink.model.Customer; 
import ledgerlink.service.AccountService; 

import java.util.List; 
import java.util.Scanner;

public class LedgerLinkApp {
    
    private static CustomerService customerService = new CustomerService(); 
    private static Customer customer = new Customer(); 
    private static AccountService accountService = new AccountService(); 

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in); 
        boolean running = true; 

        while(running) { 
            System.out.println("\n ======= LedgerLink Banking Menu =======");
            System.out.println("1. Add Customer"); 
            System.out.println("2. View all Customers");
            System.out.println("3. View Customer by ID"); 
            System.out.println("4. Update Customer by ID");
            System.out.println("5. Delete Customer by ID"); 
            System.out.println("6. Exit"); 
            System.out.println("7. Open Account"); 
            System.out.println("8. Deposit"); 
            System.out.println("9. Withdraw");
            System.out.println("10. Transfer Funds"); 
            System.out.println("11. Close Account"); 
            System.out.print("Select an option: ");

            int option = -1 ; 
            if (scanner.hasNextInt()) {
                option = scanner.nextInt(); 
                scanner.nextLine(); // consume newline
            } else {
                System.out.println("Invalid input. Please enter a valid number. ");
                scanner.nextLine(); // consume invalid input
                continue; 
            } 

            switch (option) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    viewAllCustomers(); 
                    break;
                case 3:
                    viewCustomerById(scanner); 
                    break;
                case 4:
                    updateCustomer(scanner);
                    break;
                case 5:
                    deleteCustomer(scanner);
                    break;
                case 6:
                    running = false;
                    System.out.println("Exiting LedgerLink. Goodbye!");
                    break; 
                case 7: 
                    openAccount(scanner);
                    break;
                case 8:
                    deposit(scanner);
                    break;
                case 9:
                    withdraw(scanner);
                    break;
                case 10:
                    transfer(scanner);
                    break;
                case 11:
                    closeAccount(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    } 



    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine(); 
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();  
        System.out.print("Enter customer phone: ");
        String phone = scanner.nextLine();

        Customer customer = new Customer(); 
        customer.setName(name); 
        customer.setEmail(email); 
        customer.setPhone(phone);

        boolean success = customerService.addCustomer(customer); 
        if (success) {
            System.out.println("Customer added successfully.");
        } else {
            System.out.println("Failed to add customer.");
        }
    }


    private static void viewAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers(); 
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("\n--- Customer List ---");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    } 



    private static void viewCustomerById(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int id = scanner.nextInt(); 
        scanner.nextLine(); // consume newline

        Customer customer = customerService.getCustomerById(id); 
        if (customer != null) {
            System.out.println("\n--- Customer Details ---");
            System.out.println(customer);
        } else {
            System.out.println("Customer not found with ID: " + id);
        }
    }



    private static void updateCustomer(Scanner scanner) {
        System.out.print("Enter customer ID to update: ");
        int id = scanner.nextInt(); 
        scanner.nextLine(); // consume newline

        Customer existingCustomer = customerService.getCustomerById(id); 
        if (existingCustomer == null) {
            System.out.println("Customer not found with ID: " + id);
            return;
        }

        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) customer.setName(name);

        System.out.print("Enter new email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) customer.setEmail(email); 

        System.out.print("Enter new phone (leave blank to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isBlank()) customer.setPhone(phone);

        boolean success = customerService.updateCustomer(customer); 
        if (success) {
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Failed to update customer.");
        }
    }


    private static void deleteCustomer(Scanner scanner) {
        System.out.print("Enter customer ID to delete: ");
        int id = scanner.nextInt(); 
        scanner.nextLine(); // consume newline

        boolean success = customerService.deleteCustomer(id); 
        if (success) {
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Failed to delete customer. Customer may not exist.");
        }
    } 

    private static void openAccount(Scanner scanner) { 
        try { 
            System.out.print("Customer ID: ");
            int customerId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Currency (e.g., USD, EUR, INR): ");
            String currency  = scanner.nextLine().trim().toUpperCase(); 
            System.out.print("Initial Deposit Amount: ");
            double inital = Double.parseDouble(scanner.nextLine().trim());  
            System.out.println("Branch ID (blank for default 1): ");
            String branchStr = scanner.nextLine().trim();
            Integer branchId = branchStr.isBlank() ? null: Integer.parseInt(branchStr) ; 

            boolean ok = accountService.openAccount(customerId, currency, inital, branchId) ; 
            System.out.print(ok ? "Account opened." : "Failed to open account. ") ;
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
        }
    } 

    private static void deposit(Scanner scanner) { 
        try { 
            System.out.print("Account ID: "); 
            int accountId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Deposit Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim()); 

            boolean ok = accountService.deposit(accountId, amount); 
            System.out.print(ok ? "Deposit successful." : "Failed to deposit. ") ;
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
        }
    } 

    private static void withdraw(Scanner scanner) { 
        try { 
            System.out.print("Account ID: "); 
            int accountId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Withdrawal Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim()); 

            boolean ok = accountService.withdraw(accountId, amount); 
            System.out.print(ok ? "Withdrawal successful." : "Failed to withdraw. ") ;
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
        }
    } 


    private static void transfer(Scanner scanner) { 
        try { 
            System.out.print("From Account ID: "); 
            int fromAccountId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("To Account ID: "); 
            int toAccountId = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Transfer Amount: ");
            double amount = Double.parseDouble(scanner.nextLine().trim()); 

            boolean ok = accountService.transfer(fromAccountId, toAccountId, amount); 
            System.out.print(ok ? "Transfer successful." : "Failed to transfer. ") ;
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
        }
    } 


    private static void closeAccount(Scanner scanner) { 
        try { 
            System.out.print("Account ID: "); 
            int accountId = Integer.parseInt(scanner.nextLine().trim());

            boolean ok = accountService.closeAccount(accountId); 
            System.out.print(ok ? "Account closed." : "Failed to close account. ") ;
        } catch (Exception e) { 
            System.out.println("Error: " + e.getMessage());
        }
    }
}
