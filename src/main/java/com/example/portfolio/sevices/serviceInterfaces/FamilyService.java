package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.family.FamilyCreateRequest;
import com.example.portfolio.DTOs.family.FamilyResponse;

public interface FamilyService {
    FamilyResponse createFamily(FamilyCreateRequest familyRequest);
    FamilyResponse getFamily(Long id);
    FamilyResponse updateFamily(Long id, FamilyCreateRequest familyRequest);
    void deleteFamily(Long id);
    FamilyResponse getFamilyByUserId(Long userId);

}
