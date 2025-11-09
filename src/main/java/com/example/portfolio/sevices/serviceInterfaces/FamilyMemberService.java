package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.familyMember.FamilyMemberRequest;
import com.example.portfolio.DTOs.familyMember.FamilyMemberResponse;

import java.util.List;

public interface FamilyMemberService {
    FamilyMemberResponse addMember(Long familyId, FamilyMemberRequest request);
    List<FamilyMemberResponse> getMembers(Long familyId);
    void removeMember(Long familyId, Long userId);
}
