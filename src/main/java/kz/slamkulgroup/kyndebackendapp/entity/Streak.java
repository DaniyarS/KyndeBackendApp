package kz.slamkulgroup.kyndebackendapp.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "streaks")
public class Streak {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "current_streak", nullable = false)
    private Integer currentStreak = 0;
    
    @Column(name = "longest_streak", nullable = false)
    private Integer longestStreak = 0;
    
    @Column(name = "last_updated")
    private LocalDate lastUpdated;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public Streak() {}
    
    public Streak(User user) {
        this.user = user;
        this.lastUpdated = LocalDate.now();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
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
    
    public LocalDate getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
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