package kz.slamkulgroup.kyndebackendapp.service;

import kz.slamkulgroup.kyndebackendapp.dto.UserStatsResponse;
import kz.slamkulgroup.kyndebackendapp.entity.Streak;
import kz.slamkulgroup.kyndebackendapp.entity.User;
import kz.slamkulgroup.kyndebackendapp.enums.AvatarState;
import kz.slamkulgroup.kyndebackendapp.repository.HabitRepository;
import kz.slamkulgroup.kyndebackendapp.repository.TaskRepository;
import kz.slamkulgroup.kyndebackendapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private HabitRepository habitRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User save(User user) {
        return userRepository.save(user);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }
    
    @Transactional(readOnly = true)
    public UserStatsResponse getUserStats(Long userId) {
        User user = userRepository.findByIdWithStreak(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        UserStatsResponse stats = new UserStatsResponse();
        
        Streak streak = user.getStreak();
        if (streak != null) {
            stats.setCurrentStreak(streak.getCurrentStreak());
            stats.setLongestStreak(streak.getLongestStreak());
        } else {
            stats.setCurrentStreak(0);
            stats.setLongestStreak(0);
        }
        
        Integer totalHabits = habitRepository.countByUserId(userId);
        stats.setTotalHabits(totalHabits);
        
        LocalDate today = LocalDate.now();
        Integer completedToday = taskRepository.countCompletedTasksForUserAndDate(userId, today);
        Integer totalToday = taskRepository.countTotalTasksForUserAndDate(userId, today);
        
        stats.setCompletedTasksToday(completedToday);
        stats.setTotalTasksToday(totalToday);
        
        Integer totalCompleted = taskRepository.countCompletedTasksByUserId(userId);
        Integer totalTasks = taskRepository.countTotalTasksByUserId(userId);
        
        if (totalTasks > 0) {
            stats.setCompletionRate((double) totalCompleted / totalTasks * 100);
        } else {
            stats.setCompletionRate(0.0);
        }
        
        stats.setAvatarState(user.getAvatar());
        
        return stats;
    }
    
    public void updateAvatarState(User user) {
        if (user.getStreak() == null) {
            user.setAvatar(AvatarState.SEEDLING);
            return;
        }
        
        Integer currentStreak = user.getStreak().getCurrentStreak();
        AvatarState newState;
        
        if (currentStreak == 0) {
            newState = AvatarState.WITHERED;
        } else if (currentStreak < 3) {
            newState = AvatarState.SEEDLING;
        } else if (currentStreak <= 7) {
            newState = AvatarState.SPROUT;
        } else {
            newState = AvatarState.MATURE;
        }
        
        if (user.getAvatar() != newState) {
            user.setAvatar(newState);
            userRepository.save(user);
            logger.info("Updated avatar state for user {} to {}", user.getId(), newState);
        }
    }
    
    public void updateFcmToken(Long userId, String fcmToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        logger.info("FCM token updated for user {}", userId);
    }
}