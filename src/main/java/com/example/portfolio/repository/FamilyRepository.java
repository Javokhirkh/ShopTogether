package com.example.portfolio.repository;

import com.example.portfolio.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family,Long> {
    @Query("SELECT f FROM Family f JOIN f.members m WHERE m.user.id = :userId")
    Optional<Family> findByUserId(@Param("userId") Long userId);

}
