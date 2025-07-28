package kz.slamkulgroup.kyndebackendapp.repository;

import kz.slamkulgroup.kyndebackendapp.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    @EntityGraph(attributePaths = {"streak"})
    Optional<User> findByEmail(String email);
    
    @EntityGraph(attributePaths = {"streak"})
    Optional<User> findByPhone(String phone);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.streak WHERE u.id = :id")
    Optional<User> findByIdWithStreak(@Param("id") Long id);
    
    @EntityGraph(attributePaths = {"habits", "streak"})
    Optional<User> findByIdWithHabitsAndStreak(Long id);
}