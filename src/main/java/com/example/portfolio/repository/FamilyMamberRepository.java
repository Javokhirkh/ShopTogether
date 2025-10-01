package com.example.portfolio.repository;

import com.example.portfolio.models.Family;
import com.example.portfolio.models.FamilyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyMamberRepository extends JpaRepository<FamilyMember,Long> {
}
