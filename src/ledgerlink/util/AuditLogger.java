package ledgerlink.util; 

import java.io.FileWriter; 
import java.io.IOException; 
import java.nio.file.Files;
import java.nio.file.Path; 
import java.time.Instant; 


public class AuditLogger {
    private static final String DEFAULT_PATH = "logs/audit.log"; 

    public static void log(String actor, String action, String entity, String details) { 
        ensureParent(); 

        String line = String.format("%s,%s,%s,%s,%s%n", 
        Instant.now().toString(), 
        sanitize(actor),  // Who performed the action 
        sanitize(action), // What action was performed 
        sanitize(entity), // on what resource 
        sanitize(details) // extra content
        );
        try (FileWriter fw = new FileWriter(DEFAULT_PATH, true)) { 
            fw.write(line); 
        } catch(IOException e) { 
            System.err.println("Audit log write failed: " + e.getMessage()); 
        }
    }  
    // Sanitize prevents log corruption or CSV injection 
    private static String sanitize(String s) { 
        if (s == null) return ""; 
        return s.replace("\n", " ").replace("\r", " ").replace(",", ";");
    } 

    private static void ensureParent() {   // Ensures the path  
        try { 
            Path p = Path.of(DEFAULT_PATH).getParent(); 
            if (p != null && !Files.exists(p)) { 
                Files.createDirectories(p); 
            }
        } catch (IOException e) { 
            System.err.println("Audit log directory creation failed: " + e.getMessage());
        }
    }
}
