package kz.slamkulgroup.kyndebackendapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskResponse {
    
    private Long id;
    private String habitName;
    private LocalDate dueDate;
    private boolean completed;
    private LocalDateTime completedAt;
    
    public TaskResponse() {}
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getHabitName() {
        return habitName;
    }
    
    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}