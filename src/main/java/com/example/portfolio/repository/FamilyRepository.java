package com.example.portfolio.repository;

import com.example.portfolio.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family,Long> {
    Optional<Family> findByUserId(Long userId);

}
