package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.family.FamilyCreateRequest;
import com.example.portfolio.DTOs.family.FamilyResponse;

public interface FamilyService {
    FamilyResponse create(FamilyCreateRequest familyRequest);
    FamilyResponse get(Long id);
    FamilyResponse update(Long id, FamilyCreateRequest familyRequest);
    void delete(Long id);
    FamilyResponse getFamilyByUserId(Long userId);

}
