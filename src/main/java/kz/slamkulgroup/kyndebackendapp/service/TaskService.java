package kz.slamkulgroup.kyndebackendapp.service;

import kz.slamkulgroup.kyndebackendapp.dto.TaskResponse;
import kz.slamkulgroup.kyndebackendapp.entity.Task;
import kz.slamkulgroup.kyndebackendapp.mapper.TaskMapper;
import kz.slamkulgroup.kyndebackendapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class TaskService {
    
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private TaskMapper taskMapper;
    
    @Autowired
    private StreakService streakService;
    
    @Transactional(readOnly = true)
    public Page<TaskResponse> getUserTasks(Long userId, Pageable pageable) {
        return taskRepository.findByHabitUserIdOrderByDueDateDesc(userId, pageable)
                .map(taskMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public List<TaskResponse> getUserTasksForDate(Long userId, LocalDate date) {
        return taskRepository.findByHabitUserIdAndDueDate(userId, date)
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }
    
    public TaskResponse completeTask(Long userId, Long taskId) {
        Task task = taskRepository.findByIdAndHabitUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (task.isCompleted()) {
            throw new RuntimeException("Task is already completed");
        }
        
        task.setCompleted(true);
        Task savedTask = taskRepository.save(task);
        
        streakService.updateStreakForUser(userId);
        
        logger.info("Task {} completed for user {}", taskId, userId);
        
        return taskMapper.toDto(savedTask);
    }
    
    public TaskResponse uncompleteTask(Long userId, Long taskId) {
        Task task = taskRepository.findByIdAndHabitUserId(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (!task.isCompleted()) {
            throw new RuntimeException("Task is not completed");
        }
        
        task.setCompleted(false);
        Task savedTask = taskRepository.save(task);
        
        streakService.updateStreakForUser(userId);
        
        logger.info("Task {} uncompleted for user {}", taskId, userId);
        
        return taskMapper.toDto(savedTask);
    }
}