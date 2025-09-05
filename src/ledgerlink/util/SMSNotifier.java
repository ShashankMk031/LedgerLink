package ledgerlink.util; 

public class SMSNotifier{ 
    public void send(String phone, String message) { 
        System.out.println("[SMS] to = " + phone + ", message = " + message); 
        // Future : integrate Twilio or alternate probvider 
    }
}