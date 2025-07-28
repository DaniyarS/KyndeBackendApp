package kz.slamkulgroup.kyndebackendapp.dto;

import kz.slamkulgroup.kyndebackendapp.enums.AvatarState;

public class UserStatsResponse {
    
    private Integer currentStreak;
    private Integer longestStreak;
    private Double completionRate;
    private AvatarState avatarState;
    private Integer totalHabits;
    private Integer completedTasksToday;
    private Integer totalTasksToday;
    
    public UserStatsResponse() {}
    
    public Integer getCurrentStreak() {
        return currentStreak;
    }
    
    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }
    
    public Integer getLongestStreak() {
        return longestStreak;
    }
    
    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }
    
    public Double getCompletionRate() {
        return completionRate;
    }
    
    public void setCompletionRate(Double completionRate) {
        this.completionRate = completionRate;
    }
    
    public AvatarState getAvatarState() {
        return avatarState;
    }
    
    public void setAvatarState(AvatarState avatarState) {
        this.avatarState = avatarState;
    }
    
    public Integer getTotalHabits() {
        return totalHabits;
    }
    
    public void setTotalHabits(Integer totalHabits) {
        this.totalHabits = totalHabits;
    }
    
    public Integer getCompletedTasksToday() {
        return completedTasksToday;
    }
    
    public void setCompletedTasksToday(Integer completedTasksToday) {
        this.completedTasksToday = completedTasksToday;
    }
    
    public Integer getTotalTasksToday() {
        return totalTasksToday;
    }
    
    public void setTotalTasksToday(Integer totalTasksToday) {
        this.totalTasksToday = totalTasksToday;
    }
}