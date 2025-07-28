package kz.slamkulgroup.kyndebackendapp.repository;

import kz.slamkulgroup.kyndebackendapp.entity.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    
    @EntityGraph(attributePaths = {"user"})
    Page<Habit> findByUserId(Long userId, Pageable pageable);
    
    @EntityGraph(attributePaths = {"user"})
    List<Habit> findByUserId(Long userId);
    
    @EntityGraph(attributePaths = {"user", "tasks"})
    Optional<Habit> findByIdAndUserId(Long id, Long userId);
    
    boolean existsByNameAndUserId(String name, Long userId);
    
    @Query("SELECT h FROM Habit h LEFT JOIN FETCH h.tasks t WHERE h.user.id = :userId AND t.dueDate <= CURRENT_DATE")
    List<Habit> findHabitsWithOverdueTasks(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(h) FROM Habit h WHERE h.user.id = :userId")
    Integer countByUserId(@Param("userId") Long userId);
}