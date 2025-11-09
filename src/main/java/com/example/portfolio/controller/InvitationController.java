package com.example.portfolio.controller;

import com.example.portfolio.DTOs.invitation.InvitationRequest;
import com.example.portfolio.DTOs.invitation.InvitationResponse;
import com.example.portfolio.sevices.serviceInterfaces.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {
    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvitationResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.get(id));
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<InvitationResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(invitationService.getByEmail(email));
    }

    @GetMapping("/family/{familyId}")
    public ResponseEntity<InvitationResponse> getByFamily(@PathVariable Long familyId) {
        return ResponseEntity.ok(invitationService.getByFamilyId(familyId));
    }

    @PostMapping
    public ResponseEntity<InvitationResponse> send(@RequestBody InvitationRequest request) {
        InvitationResponse response = invitationService.send(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvitationResponse> update(@PathVariable Long id,
                                                     @RequestBody InvitationRequest request) {
        InvitationResponse response = invitationService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<InvitationResponse> accept(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.accept(id));
    }

    @PostMapping("/{id}/decline")
    public ResponseEntity<InvitationResponse> decline(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.decline(id));
    }

    @PostMapping("/{id}/resend")
    public ResponseEntity<InvitationResponse> resend(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.resend(id));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<InvitationResponse> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(invitationService.cancel(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        invitationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
