package kz.slamkulgroup.kyndebackendapp.scheduler;

import kz.slamkulgroup.kyndebackendapp.service.StreakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StreakCalculatorScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(StreakCalculatorScheduler.class);
    
    @Autowired
    private StreakService streakService;
    
    @Scheduled(cron = "0 59 23 * * *")
    public void updateStreaks() {
        logger.info("Starting streak calculation...");
        
        try {
            streakService.updateAllStreaks();
            logger.info("Streak calculation completed successfully");
        } catch (Exception e) {
            logger.error("Error during streak calculation", e);
        }
    }
}