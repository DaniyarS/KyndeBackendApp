package kz.slamkulgroup.kyndebackendapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.slamkulgroup.kyndebackendapp.enums.ScheduleType;

import java.time.DayOfWeek;
import java.util.List;

public class HabitRequest {
    
    @NotBlank(message = "Habit name is required")
    private String name;
    
    private String description;
    
    @NotNull(message = "Schedule type is required")
    private ScheduleType scheduleType;
    
    private Integer interval;
    
    private List<DayOfWeek> customDays;
    
    public HabitRequest() {}
    
    public HabitRequest(String name, String description, ScheduleType scheduleType, Integer interval, List<DayOfWeek> customDays) {
        this.name = name;
        this.description = description;
        this.scheduleType = scheduleType;
        this.interval = interval;
        this.customDays = customDays;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public ScheduleType getScheduleType() {
        return scheduleType;
    }
    
    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }
    
    public Integer getInterval() {
        return interval;
    }
    
    public void setInterval(Integer interval) {
        this.interval = interval;
    }
    
    public List<DayOfWeek> getCustomDays() {
        return customDays;
    }
    
    public void setCustomDays(List<DayOfWeek> customDays) {
        this.customDays = customDays;
    }
}