package ledgerlink.service; 

import java.io.FileWriter; 
import java.io.IOException; 
import java.time.Instant; 
import java.util.HashMap; 
import java.util.Map; 

/*
 * It logs the alerts to log/fraud.log so that it can be investigated. 
 * the hashmap keeps the last transaction timestamp per account . and checks the rules and logs 
 * 
 */

public class FraudService { 
    // Track last transaction times and flag for rapid activity 
    private final Map<Integer, Instant> lastTxTime = new HashMap<>(); 

    private static final String ALERT_PATH = "logs/fraud.log"; 
    private static final double LARGE_AMOUNT = 1000000.0; 

    public void checkTransaction(int accountId, String type, double amount) { 
        boolean suspicious = false; 
        StringBuilder reason = new StringBuilder(); 

        //rule 1: unusuall large amount 
        if (amount >= LARGE_AMOUNT) { 
            suspicious = true; 
            reason.append("Large transaction of ").append(amount).append("; "); 

        } 
        // Rule 2 : trasnaction within 10 seconds of previous 
        Instant now = Instant.now(); 
        if (lastTxTime.containsKey(accountId)) {
            Instant prev = lastTxTime.get(accountId);
            if(now.minusSeconds(10).isBefore(prev)) { 
                suspicious = true; 
                reason.append("Rapid transaction within 10s; ");
            }
        } 
        lastTxTime.put(accountId, now); 

        if(suspicious) { 
            writeAlert(accountId, type, amount, reason.toString());
        }
    } 

    private void writeAlert(int accountId, String type, double amount, String reason) { 
        String line = String.format("%s, accountId = %s, type = %s, amount = %.2f, reason = %s%n", Instant.now(), accountId, type, amount, reason);
        try(FileWriter fw = new FileWriter(ALERT_PATH, true)) {
            fw.write(line);
        } catch (IOException e) { 
            System.err.println("Fraud alert log failed: " + e.getMessage());
        }
    }
}