package com.example.portfolio.repository;

import com.example.portfolio.models.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
InvitationRepository extends JpaRepository<Invitation,Long> {
    Optional<Invitation> findByRecipientEmail(String email);
    Optional<Invitation> findByFamilyId(Long familyId);
}
