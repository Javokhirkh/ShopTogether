package com.example.portfolio.controller;

import com.example.portfolio.DTOs.family.FamilyCreateRequest;
import com.example.portfolio.DTOs.family.FamilyResponse;
import com.example.portfolio.sevices.serviceInterfaces.FamilyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/families")
public class FamilyController {

    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PostMapping
    public ResponseEntity<FamilyResponse> create(@RequestBody FamilyCreateRequest request) {
        FamilyResponse response = familyService.create(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyResponse> get(@PathVariable Long id) {
        FamilyResponse response = familyService.get(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FamilyResponse> update(
            @PathVariable Long id,
            @RequestBody FamilyCreateRequest request) {
        FamilyResponse response = familyService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        familyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<FamilyResponse> getFamilyByUser(@PathVariable Long userId) {
        FamilyResponse response = familyService.getFamilyByUserId(userId);
        return ResponseEntity.ok(response);
    }
}
