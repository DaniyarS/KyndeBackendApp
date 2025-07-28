package kz.slamkulgroup.kyndebackendapp.service;

import kz.slamkulgroup.kyndebackendapp.entity.Streak;
import kz.slamkulgroup.kyndebackendapp.entity.Task;
import kz.slamkulgroup.kyndebackendapp.entity.User;
import kz.slamkulgroup.kyndebackendapp.repository.StreakRepository;
import kz.slamkulgroup.kyndebackendapp.repository.TaskRepository;
import kz.slamkulgroup.kyndebackendapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class StreakService {
    
    private static final Logger logger = LoggerFactory.getLogger(StreakService.class);
    
    @Autowired
    private StreakRepository streakRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;
    
    public void updateStreakForUser(Long userId) {
        Streak streak = streakRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Streak not found for user"));
        
        updateStreakCalculation(streak);
        streakRepository.save(streak);
    }
    
    public void updateAllStreaks() {
        LocalDate today = LocalDate.now();
        List<Streak> streaksToUpdate = streakRepository.findStreaksToUpdateWithUser(today);
        
        for (Streak streak : streaksToUpdate) {
            updateStreakCalculation(streak);
            streakRepository.save(streak);
            
            User user = streak.getUser();
            userService.updateAvatarState(user);
        }
        
        logger.info("Updated {} streaks", streaksToUpdate.size());
    }
    
    private void updateStreakCalculation(Streak streak) {
        LocalDate today = LocalDate.now();
        Long userId = streak.getUser().getId();
        
        int consecutiveDays = 0;
        LocalDate checkDate = today;
        
        while (true) {
            List<Task> tasksForDate = taskRepository.findTasksForUserAndDate(userId, checkDate);
            
            if (tasksForDate.isEmpty()) {
                checkDate = checkDate.minusDays(1);
                continue;
            }
            
            boolean allCompleted = tasksForDate.stream().allMatch(Task::isCompleted);
            
            if (allCompleted) {
                consecutiveDays++;
                checkDate = checkDate.minusDays(1);
            } else {
                break;
            }
            
            if (consecutiveDays > 365) {
                break;
            }
        }
        
        streak.setCurrentStreak(consecutiveDays);
        
        if (consecutiveDays > streak.getLongestStreak()) {
            streak.setLongestStreak(consecutiveDays);
        }
        
        streak.setLastUpdated(today);
        
        logger.debug("Updated streak for user {}: current={}, longest={}", 
                    userId, consecutiveDays, streak.getLongestStreak());
    }
}