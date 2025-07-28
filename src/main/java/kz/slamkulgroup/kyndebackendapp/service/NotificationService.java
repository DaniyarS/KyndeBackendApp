package kz.slamkulgroup.kyndebackendapp.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kz.slamkulgroup.kyndebackendapp.entity.NotificationSetting;
import kz.slamkulgroup.kyndebackendapp.repository.NotificationSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class NotificationService {
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    
    @Autowired
    private NotificationSettingRepository notificationSettingRepository;
    
    @Autowired(required = false)
    private FirebaseMessaging firebaseMessaging;
    
    public void sendPendingNotifications() {
        if (firebaseMessaging == null) {
            logger.debug("Firebase messaging not configured, skipping notifications");
            return;
        }
        
        LocalTime now = LocalTime.now();
        LocalTime startTime = now.minusMinutes(15);
        LocalTime endTime = now.plusMinutes(15);
        DayOfWeek today = java.time.LocalDate.now().getDayOfWeek();
        
        List<NotificationSetting> notifications = notificationSettingRepository
                .findActiveNotificationsForTimeRangeAndDay(startTime, endTime, today);
        
        int sentCount = 0;
        
        for (NotificationSetting notification : notifications) {
            try {
                sendHabitReminder(notification);
                sentCount++;
            } catch (Exception e) {
                logger.error("Failed to send notification for habit {}: {}", 
                           notification.getHabit().getName(), e.getMessage());
            }
        }
        
        if (sentCount > 0) {
            logger.info("Sent {} habit reminder notifications", sentCount);
        }
    }
    
    private void sendHabitReminder(NotificationSetting notificationSetting) 
            throws FirebaseMessagingException {
        
        if (notificationSetting.getFcmToken() == null || notificationSetting.getFcmToken().isEmpty()) {
            logger.warn("No FCM token for notification setting {}", notificationSetting.getId());
            return;
        }
        
        String habitName = notificationSetting.getHabit().getName();
        String title = "Habit Reminder";
        String body = String.format("Time to complete your habit: %s", habitName);
        
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        
        Message message = Message.builder()
                .setToken(notificationSetting.getFcmToken())
                .setNotification(notification)
                .putData("habitId", notificationSetting.getHabit().getId().toString())
                .putData("type", "habit_reminder")
                .build();
        
        String response = firebaseMessaging.send(message);
        
        logger.debug("Sent notification for habit '{}' to user {}: {}", 
                   habitName, 
                   notificationSetting.getHabit().getUser().getId(), 
                   response);
    }
    
    public void sendCustomNotification(String token, String title, String body) 
            throws FirebaseMessagingException {
        
        if (firebaseMessaging == null) {
            logger.warn("Firebase messaging not configured, cannot send notification");
            return;
        }
        
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();
        
        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();
        
        String response = firebaseMessaging.send(message);
        logger.info("Sent custom notification: {}", response);
    }
}