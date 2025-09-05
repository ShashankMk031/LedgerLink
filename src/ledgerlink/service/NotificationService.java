package ledgerlink.service;

import ledgerlink.util.EmailSender;
import ledgerlink.util.SMSNotifier; 

public class NotificationService{ 

    private final EmailSender emailSender = new EmailSender(); 
    private final SMSNotifier smsNotifier = new SMSNotifier(); 

    public void notifyEmail(String to, String subject, String body) { 
        emailSender.send( to, subject, body);
    } 

    public void notifySMS(String phone, String message) { 
        smsNotifier.send(phone, message); 
    } 
}