package kz.slamkulgroup.kyndebackendapp.repository;

import kz.slamkulgroup.kyndebackendapp.entity.NotificationSetting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    
    @EntityGraph(attributePaths = {"habit", "habit.user"})
    List<NotificationSetting> findByHabitUserId(Long userId);
    
    @Query("SELECT ns FROM NotificationSetting ns JOIN FETCH ns.habit h JOIN FETCH h.user " +
           "WHERE ns.active = true AND ns.triggerTime BETWEEN :startTime AND :endTime " +
           "AND :dayOfWeek MEMBER OF ns.days")
    List<NotificationSetting> findActiveNotificationsForTimeRangeAndDay(
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("dayOfWeek") DayOfWeek dayOfWeek);
    
    @Query("SELECT ns FROM NotificationSetting ns JOIN FETCH ns.habit h JOIN FETCH h.user " +
           "WHERE ns.active = true AND ns.fcmToken IS NOT NULL")
    List<NotificationSetting> findActiveNotificationsWithTokens();
    
    void deleteByHabitId(Long habitId);
}