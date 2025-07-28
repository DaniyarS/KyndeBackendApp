package kz.slamkulgroup.kyndebackendapp.repository;

import kz.slamkulgroup.kyndebackendapp.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    @EntityGraph(attributePaths = {"habit", "habit.user"})
    Optional<Task> findByIdAndHabitUserId(Long id, Long userId);
    
    @EntityGraph(attributePaths = {"habit"})
    Page<Task> findByHabitUserIdOrderByDueDateDesc(Long userId, Pageable pageable);
    
    @EntityGraph(attributePaths = {"habit"})
    List<Task> findByHabitUserIdAndDueDate(Long userId, LocalDate dueDate);
    
    @Query("SELECT t FROM Task t JOIN FETCH t.habit h WHERE h.user.id = :userId AND t.dueDate = :date")
    List<Task> findTasksForUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Query("SELECT COUNT(t) FROM Task t JOIN t.habit h WHERE h.user.id = :userId AND t.dueDate = :date AND t.completed = true")
    Integer countCompletedTasksForUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Query("SELECT COUNT(t) FROM Task t JOIN t.habit h WHERE h.user.id = :userId AND t.dueDate = :date")
    Integer countTotalTasksForUserAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Query("SELECT t FROM Task t JOIN FETCH t.habit h WHERE h.user.id = :userId AND t.dueDate BETWEEN :startDate AND :endDate")
    List<Task> findTasksForUserBetweenDates(@Param("userId") Long userId, 
                                           @Param("startDate") LocalDate startDate, 
                                           @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(t) FROM Task t JOIN t.habit h WHERE h.user.id = :userId AND t.completed = true")
    Integer countCompletedTasksByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(t) FROM Task t JOIN t.habit h WHERE h.user.id = :userId")
    Integer countTotalTasksByUserId(@Param("userId") Long userId);
    
    boolean existsByHabitIdAndDueDate(Long habitId, LocalDate dueDate);
}