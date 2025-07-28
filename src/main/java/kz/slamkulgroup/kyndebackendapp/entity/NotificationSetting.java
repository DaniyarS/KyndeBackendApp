package kz.slamkulgroup.kyndebackendapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "notification_settings")
public class NotificationSetting {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;
    
    @Column(name = "trigger_time", nullable = false)
    private LocalTime triggerTime;
    
    @Enumerated(EnumType.STRING)
    @ElementCollection
    @CollectionTable(name = "notification_days", joinColumns = @JoinColumn(name = "notification_id"))
    @Column(name = "day_of_week")
    private List<DayOfWeek> days;
    
    @Column(nullable = false)
    private boolean active = true;
    
    @Column(name = "fcm_token")
    private String fcmToken;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public NotificationSetting() {}
    
    public NotificationSetting(Habit habit, LocalTime triggerTime, List<DayOfWeek> days, String fcmToken) {
        this.habit = habit;
        this.triggerTime = triggerTime;
        this.days = days;
        this.fcmToken = fcmToken;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Habit getHabit() {
        return habit;
    }
    
    public void setHabit(Habit habit) {
        this.habit = habit;
    }
    
    public LocalTime getTriggerTime() {
        return triggerTime;
    }
    
    public void setTriggerTime(LocalTime triggerTime) {
        this.triggerTime = triggerTime;
    }
    
    public List<DayOfWeek> getDays() {
        return days;
    }
    
    public void setDays(List<DayOfWeek> days) {
        this.days = days;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getFcmToken() {
        return fcmToken;
    }
    
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
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