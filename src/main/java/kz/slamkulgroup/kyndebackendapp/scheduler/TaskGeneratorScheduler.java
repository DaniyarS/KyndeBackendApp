package kz.slamkulgroup.kyndebackendapp.scheduler;

import kz.slamkulgroup.kyndebackendapp.service.TaskGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskGeneratorScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorScheduler.class);
    
    @Autowired
    private TaskGeneratorService taskGeneratorService;
    
    @Scheduled(cron = "0 1 0 * * *")
    public void generateDailyTasks() {
        logger.info("Starting daily task generation...");
        
        try {
            taskGeneratorService.generateDailyTasks();
            logger.info("Daily task generation completed successfully");
        } catch (Exception e) {
            logger.error("Error during daily task generation", e);
        }
    }
}