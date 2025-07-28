package kz.slamkulgroup.kyndebackendapp.dto;

import kz.slamkulgroup.kyndebackendapp.enums.ScheduleType;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

public class HabitResponse {
    
    private Long id;
    private String name;
    private String description;
    private ScheduleType scheduleType;
    private Integer interval;
    private List<DayOfWeek> customDays;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public HabitResponse() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}