package com.example.portfolio.controller;


import com.example.portfolio.DTOs.familyMember.FamilyMemberRequest;
import com.example.portfolio.DTOs.familyMember.FamilyMemberResponse;
import com.example.portfolio.sevices.serviceInterfaces.FamilyMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/families/{familyId}/members")
public class FamilyMemberController {

    private final FamilyMemberService memberService;

    public FamilyMemberController(FamilyMemberService memberService) {
        this.memberService = memberService;
    }

    // Add a member to a family
    @PostMapping
    public ResponseEntity<FamilyMemberResponse> addMember(
            @PathVariable Long familyId,
            @RequestBody FamilyMemberRequest request
    ) {
        FamilyMemberResponse response = memberService.addMember(familyId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get all members of a family
    @GetMapping
    public ResponseEntity<List<FamilyMemberResponse>> getMembers(@PathVariable Long familyId) {
        List<FamilyMemberResponse> members = memberService.getMembers(familyId);
        return ResponseEntity.ok(members);
    }

    // Remove a member from a family
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long familyId,
            @PathVariable Long userId
    ) {
        memberService.removeMember(familyId, userId);
        return ResponseEntity.noContent().build();
    }
}
