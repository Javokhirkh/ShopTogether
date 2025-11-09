package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.invitation.InvitationRequest;
import com.example.portfolio.DTOs.invitation.InvitationResponse;

public interface InvitationService {
    InvitationResponse get(Long id);

    InvitationResponse getByEmail(String email);

    InvitationResponse getByFamilyId(Long familyId);

    void delete(Long id);
    InvitationResponse accept(Long id);
    InvitationResponse decline(Long id);
    InvitationResponse resend(Long id);
    InvitationResponse cancel(Long id);
    InvitationResponse send(InvitationRequest request);
    InvitationResponse update(Long id, InvitationRequest request);

}
