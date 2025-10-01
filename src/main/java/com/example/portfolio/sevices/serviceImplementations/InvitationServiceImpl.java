package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.invitation.InvitationRequest;
import com.example.portfolio.DTOs.invitation.InvitationResponse;
import com.example.portfolio.sevices.serviceInterfaces.InvitationService;

public class InvitationServiceImpl implements InvitationService {
    @Override
    public InvitationResponse getInvitation(Long id) {
        return null;
    }

    @Override
    public void deleteInvitation(Long id) {

    }

    @Override
    public InvitationResponse acceptInvitation(Long id) {
        return null;
    }

    @Override
    public InvitationResponse declineInvitation(Long id) {
        return null;
    }

    @Override
    public InvitationResponse resendInvitation(Long id) {
        return null;
    }

    @Override
    public InvitationResponse cancelInvitation(Long id) {
        return null;
    }

    @Override
    public InvitationResponse sendInvitation(InvitationRequest request) {
        return null;
    }

    @Override
    public InvitationResponse updateInvitation(Long id, InvitationRequest request) {
        return null;
    }
}
