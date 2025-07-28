package kz.slamkulgroup.kyndebackendapp.service;

import kz.slamkulgroup.kyndebackendapp.entity.Habit;
import kz.slamkulgroup.kyndebackendapp.entity.Task;
import kz.slamkulgroup.kyndebackendapp.enums.ScheduleType;
import kz.slamkulgroup.kyndebackendapp.repository.HabitRepository;
import kz.slamkulgroup.kyndebackendapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaskGeneratorService {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskGeneratorService.class);
    
    @Autowired
    private HabitRepository habitRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    public void generateDailyTasks() {
        LocalDate today = LocalDate.now();
        List<Habit> allHabits = habitRepository.findAll();
        
        int generatedTasks = 0;
        
        for (Habit habit : allHabits) {
            if (shouldGenerateTaskForDate(habit, today)) {
                if (!taskRepository.existsByHabitIdAndDueDate(habit.getId(), today)) {
                    Task task = new Task(habit, today);
                    taskRepository.save(task);
                    generatedTasks++;
                }
            }
        }
        
        logger.info("Generated {} tasks for date {}", generatedTasks, today);
    }
    
    private boolean shouldGenerateTaskForDate(Habit habit, LocalDate date) {
        if (habit.getSchedule() == null) {
            return false;
        }
        
        ScheduleType scheduleType = habit.getSchedule().getType();
        
        switch (scheduleType) {
            case DAILY:
                return shouldGenerateForDaily(habit, date);
            case WEEKLY:
                return shouldGenerateForWeekly(habit, date);
            case CUSTOM:
                return shouldGenerateForCustom(habit, date);
            default:
                return false;
        }
    }
    
    private boolean shouldGenerateForDaily(Habit habit, LocalDate date) {
        Integer interval = habit.getSchedule().getInterval();
        if (interval == null || interval <= 0) {
            interval = 1;
        }
        
        LocalDate habitCreated = habit.getCreatedAt().toLocalDate();
        long daysSinceCreation = java.time.temporal.ChronoUnit.DAYS.between(habitCreated, date);
        
        return daysSinceCreation % interval == 0;
    }
    
    private boolean shouldGenerateForWeekly(Habit habit, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<DayOfWeek> customDays = habit.getSchedule().getCustomDays();
        
        if (customDays == null || customDays.isEmpty()) {
            return dayOfWeek == DayOfWeek.MONDAY;
        }
        
        return customDays.contains(dayOfWeek);
    }
    
    private boolean shouldGenerateForCustom(Habit habit, LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        List<DayOfWeek> customDays = habit.getSchedule().getCustomDays();
        
        if (customDays == null || customDays.isEmpty()) {
            return false;
        }
        
        return customDays.contains(dayOfWeek);
    }
}