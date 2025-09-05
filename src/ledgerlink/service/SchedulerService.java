package ledgerlink.service; 

import java.util.ArrayList; 
import java.util.List; 
import java.util.concurrent.*; 

public class SchedulerService { 

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2); // 2 scheduled jobs can run parallel. 

    private final List<ScheduledFuture<?>> tasks = new ArrayList<>(); // Needed to cancel scheduled jobs when stopping the service.

    public void start() { 
        // Example placeholders; real jobs added via scheduleFixedRate(...) , like run interest calculation every 24 hours 
    } 

    public void stop() { //Cancels each scheduled job.
        for (ScheduledFuture<?> f:tasks) { 
            f.cancel(false); //false means don't interrupt if already running 
        } 
        scheduler.shutdown(); // ensures clean shutdown - needed for logging 
    } 
    /* 
     * initialDelaySeconds -> wait time before first execution.
     * periodSeconds-> how often to repeat 
     * scheduleAtFixedRate-> job runs at fixed intervals
     */
    public void scheduleFixedRate(Runnable job, long initialDelaySeconds, long periodSeconds) {  // Accepts a RUnnable(job to run) 
        ScheduledFuture<?> f = scheduler.scheduleAtFixedRate(job, initialDelaySeconds, periodSeconds, TimeUnit.SECONDS);
        tasks.add(f);
    } 

    public boolean isRunning() {  // True if scheduler is still active 
        return !scheduler.isShutdown();
    }
} 

/* 
 * This handles background business logic automatically- 
 * Encapsulates scheduling logic. 
 * Can scale the thread pool if workload grows. 
 */ 

 /* 
  * The core purpose of this class is to give your LedgerLink system the ability to
   run jobs automatically in the background, without needing manual intervention.
  * Why ScheduleExecutorService -> has thread pool , and can schedule jobs to run at fixed intervals, hadles exception 
    safely - a single error won't kill the whole system. , Can manage all job centrally. 
  */