package ledgerlink.util; 

public class EmailSender { 
    public  void send(String to, String subject, String body) { 
        System.out.println("[EMAIL] to = " + to + ", subject = " + subject + ", body = " + body); 
        // Need to integrate JavaMail or external SMTP
    }
}