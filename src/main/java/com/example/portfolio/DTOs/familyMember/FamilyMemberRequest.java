package com.example.portfolio.DTOs.familyMember;

import com.example.portfolio.enums.FamilyRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FamilyMemberRequest {
    private Long userId;
    private FamilyRole role;
}
