package com.example.portfolio.repository;

import com.example.portfolio.models.Family;
import com.example.portfolio.models.FamilyMember;
import com.example.portfolio.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {
    List<FamilyMember> findAllByFamilyId(Long familyId);
    List<FamilyMember> findByUser(User user);
    Optional<FamilyMember> findByFamilyAndUser(Family family, User user);
    long countByFamily(Family family);
}
