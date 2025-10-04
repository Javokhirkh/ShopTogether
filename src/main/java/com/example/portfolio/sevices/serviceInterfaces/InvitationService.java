package com.example.portfolio.sevices.serviceInterfaces;

import com.example.portfolio.DTOs.invitation.InvitationRequest;
import com.example.portfolio.DTOs.invitation.InvitationResponse;

public interface InvitationService {
    InvitationResponse getInvitation(Long id);

    InvitationResponse getInvitationByEmail(String email);

    InvitationResponse getInvitationByFamilyId(Long familyId);

    void deleteInvitation(Long id);
    InvitationResponse acceptInvitation(Long id);
    InvitationResponse declineInvitation(Long id);
    InvitationResponse resendInvitation(Long id);
    InvitationResponse cancelInvitation(Long id);
    InvitationResponse sendInvitation(InvitationRequest request);
    InvitationResponse updateInvitation(Long id, InvitationRequest request);

}
