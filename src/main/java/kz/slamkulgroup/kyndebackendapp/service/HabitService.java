package kz.slamkulgroup.kyndebackendapp.service;

import kz.slamkulgroup.kyndebackendapp.dto.HabitRequest;
import kz.slamkulgroup.kyndebackendapp.dto.HabitResponse;
import kz.slamkulgroup.kyndebackendapp.entity.Habit;
import kz.slamkulgroup.kyndebackendapp.entity.User;
import kz.slamkulgroup.kyndebackendapp.mapper.HabitMapper;
import kz.slamkulgroup.kyndebackendapp.repository.HabitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitService {
    
    private static final Logger logger = LoggerFactory.getLogger(HabitService.class);
    
    @Autowired
    private HabitRepository habitRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private HabitMapper habitMapper;
    
    @Transactional(readOnly = true)
    public Page<HabitResponse> getUserHabits(Long userId, Pageable pageable) {
        return habitRepository.findByUserId(userId, pageable)
                .map(habitMapper::toDto);
    }
    
    @Transactional(readOnly = true)
    public List<HabitResponse> getUserHabits(Long userId) {
        return habitRepository.findByUserId(userId)
                .stream()
                .map(habitMapper::toDto)
                .toList();
    }
    
    public HabitResponse createHabit(Long userId, HabitRequest habitRequest) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (habitRepository.existsByNameAndUserId(habitRequest.getName(), userId)) {
            throw new RuntimeException("Habit with this name already exists");
        }
        
        Habit habit = habitMapper.toEntity(habitRequest);
        habit.setUser(user);
        
        Habit savedHabit = habitRepository.save(habit);
        
        logger.info("Created habit '{}' for user {}", habitRequest.getName(), userId);
        
        return habitMapper.toDto(savedHabit);
    }
    
    public HabitResponse updateHabit(Long userId, Long habitId, HabitRequest habitRequest) {
        Habit habit = habitRepository.findByIdAndUserId(habitId, userId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        if (!habit.getName().equals(habitRequest.getName()) && 
            habitRepository.existsByNameAndUserId(habitRequest.getName(), userId)) {
            throw new RuntimeException("Habit with this name already exists");
        }
        
        habit.setName(habitRequest.getName());
        habit.setDescription(habitRequest.getDescription());
        habit.setSchedule(habitMapper.mapToScheduleConfig(habitRequest));
        
        Habit savedHabit = habitRepository.save(habit);
        
        logger.info("Updated habit '{}' for user {}", habitRequest.getName(), userId);
        
        return habitMapper.toDto(savedHabit);
    }
    
    public void deleteHabit(Long userId, Long habitId) {
        Habit habit = habitRepository.findByIdAndUserId(habitId, userId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        habitRepository.delete(habit);
        
        logger.info("Deleted habit '{}' for user {}", habit.getName(), userId);
    }
    
    @Transactional(readOnly = true)
    public HabitResponse getHabit(Long userId, Long habitId) {
        Habit habit = habitRepository.findByIdAndUserId(habitId, userId)
                .orElseThrow(() -> new RuntimeException("Habit not found"));
        
        return habitMapper.toDto(habit);
    }
}