package com.example.portfolio.DTOs.familyMember;

import com.example.portfolio.enums.FamilyRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMemberResponse {
    private Long id;
    private Long userId;
    private String username;
    private Long familyId;
    private String familyName;
    private FamilyRole role;
    private LocalDateTime joinedAt;
}