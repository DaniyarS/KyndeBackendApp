package kz.slamkulgroup.kyndebackendapp.scheduler;

import kz.slamkulgroup.kyndebackendapp.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationDispatcherScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationDispatcherScheduler.class);
    
    @Autowired
    private NotificationService notificationService;
    
    @Scheduled(fixedRate = 1800000)
    public void sendPendingNotifications() {
        logger.debug("Checking for pending notifications...");
        
        try {
            notificationService.sendPendingNotifications();
        } catch (Exception e) {
            logger.error("Error during notification dispatch", e);
        }
    }
}