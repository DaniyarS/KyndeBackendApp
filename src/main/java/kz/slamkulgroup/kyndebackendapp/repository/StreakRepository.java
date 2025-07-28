package kz.slamkulgroup.kyndebackendapp.repository;

import kz.slamkulgroup.kyndebackendapp.entity.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StreakRepository extends JpaRepository<Streak, Long> {
    
    Optional<Streak> findByUserId(Long userId);
    
    @Query("SELECT s FROM Streak s WHERE s.lastUpdated < :date")
    List<Streak> findStreaksToUpdate(@Param("date") LocalDate date);
    
    @Query("SELECT s FROM Streak s JOIN FETCH s.user WHERE s.lastUpdated < :date")
    List<Streak> findStreaksToUpdateWithUser(@Param("date") LocalDate date);
}