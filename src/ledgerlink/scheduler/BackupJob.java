package ledgerlink.scheduler; 

import java.io.File; 
import java.io.IOException; 

public class BackupJob{ 

    private final String dbUrl; 
    private final String dbUser; 
    private final String dbPassword; 
    private final String dbName; 

    public BackupJob(String dbUrl, String dbUser, String dbPassword) { 
        this.dbUrl = dbUrl; 
        this.dbUser = dbUser; 
        this.dbPassword = dbPassword; 
        this.dbName = extractDbName(dbUrl); 


    } 

    private String extractDbName(String url) { 
        // parse : jdbc:mysql://host:port/dbname?params 
        int idx1 = url.lastIndexOf("/"); 
        int idx2 = url.indexOf("?", idx1); 
        if (idx1 < 0) return "ledgerlink"; 
        return idx2 > idx1 ? url.substring(idx1 + 1, idx2) : url.substring(idx1 + 1);
    }

    //Export DB schema and data to file 
    public void exportDatabase(String outputFile) throws IOException , InterruptedException{
        String cmd = String.format("mysqldump -u%s -p%s %s > %s", dbUser, dbPassword, dbName, outputFile);
        runBash(cmd); 
    } 

    // Import DB from file 
    public void importDatabase(String inputFile) throws IOException, InterruptedException {
        String cmd = String.format("mysql -u%s -p%s %s < %s", dbUser, dbPassword , dbName, inputFile);
        runBash(cmd); 
    } 

    //To execute shell 
    private void runBash(String cmd) throws IOException, InterruptedException {
        String[] fullCmd = {"/bin/bash", "-c", cmd};
        Process proc = Runtime.getRuntime().exec(fullCmd); 
        proc.waitFor();
    }
} 