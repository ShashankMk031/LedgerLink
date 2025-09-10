package ledgerlink.scheduler; 

import ledgerlink.service.SchedulerService; 

public class SchedulerManager{ 

    private final SchedulerService scheduler; 
    private final BackupJob backupJob; 

    public SchedulerManager(SchedulerService scheduler, BackupJob backupJob) { 
        this.scheduler = scheduler; 
        this.backupJob = backupJob;
    } 

    public void scheduleNightlyBackup() { 
        scheduler.scheduleFixedRate(() -> { 
            try { 
                backupJob.exportDatabase("backups/ledgerlink_backup.sql");
                System.out.println("[BACKUP] Database export complete. "); 
            } catch (Exception e) { 
                System.err.println("[BACKUP] Failes: " + e.getMessage()); 
            } 
        } , 10, 86400); // First runn after 10s; every 24hrs 
    }
} 