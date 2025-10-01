package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.family.FamilyCreateRequest;
import com.example.portfolio.DTOs.family.FamilyResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.Family;
import com.example.portfolio.repository.FamilyRepository;
import com.example.portfolio.sevices.serviceInterfaces.FamilyService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository repository;

    public FamilyServiceImpl(FamilyRepository repository) {
        this.repository = repository;
    }
    @Override
    public FamilyResponse createFamily(FamilyCreateRequest familyRequest) {
        var family = mapDtoToEntity(familyRequest);
        var savedFamily = repository.save(family);
        return mapEntityToDto(savedFamily);
    }

    @Override
    public FamilyResponse getFamily(Long id) {
        return repository.findById(id)
                .map(this::mapEntityToDto)
                .orElseThrow(()->new ResourceNotFoundException("Family not found with id: " + id));
    }

    @Override
    public FamilyResponse updateFamily(Long id, FamilyCreateRequest familyRequest) {
        var existingFamily = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found with id: " + id));

        if (familyRequest.getName() != null && !familyRequest.getName().isBlank()) {
            existingFamily.setName(familyRequest.getName());
        }

        var updatedFamily = repository.save(existingFamily);
        return mapEntityToDto(updatedFamily);
    }

    @Override
    public void deleteFamily(Long id) {
        repository.deleteById(id);
    }

    @Override
    public FamilyResponse getFamilyByUserId(Long userId) {
        var family=repository.findByUserId(userId)
                .orElseThrow(()->new ResourceNotFoundException("Family not found for user id: " + userId));
        return mapEntityToDto(family);
    }

    private Family mapDtoToEntity(FamilyCreateRequest familyRequest) {
        return Family.builder()
                .name(familyRequest.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private FamilyResponse mapEntityToDto(Family savedFamily) {
        return FamilyResponse.builder()
                .id(savedFamily.getId())
                .name(savedFamily.getName())
                .createdAt(savedFamily.getCreatedAt())
                .build();
    }
}
