package ledgerlink.model; 

// POJO - Plain Old Java Object representing a Customer entity -> Building blocks for application logic

public class Customer { 
    private int customerId;
    private String name;
    private String email;
    private String phone;

    // Constructor 
    public Customer() {   // No arg constructor - usefull when creating empty objects to set values later for JDBC, Hibernate ,Spring 
    }
    public Customer (int customerId, String name , String email, String phone) {  // Parameterized constructor - handy when all fileds are known
        this.customerId = customerId; 
        this.name = name; 
        this.email = email; 
        this.phone = phone; 
    } 

    // Getters and Setters  - Used since the fields are private
    public int getCustomerId() { 
        return customerId; 
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    } 

    // Override toString for easy debugging and printing 

    @Override 
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}