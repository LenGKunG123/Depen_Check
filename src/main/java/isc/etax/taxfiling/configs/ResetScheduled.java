package isc.etax.taxfiling.configs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ResetScheduled {
	
	@Scheduled(cron = "0 0 0 1 * ?")
    public void runMonthlyTask() {
        System.out.println("Executing scheduled task every month");
        // Add your logic here (e.g., send request to another service)
    }
	
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void runYearlyTask() {
        System.out.println("Executing scheduled task every year");
        // Add your logic here (e.g., send request to another service)
    }
    
    

}
