package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.invitation.InvitationRequest;
import com.example.portfolio.DTOs.invitation.InvitationResponse;
import com.example.portfolio.enums.InvitationStatus;
import com.example.portfolio.models.Invitation;
import com.example.portfolio.repository.InvitationRepository;
import com.example.portfolio.sevices.serviceInterfaces.InvitationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository repository;

    public InvitationServiceImpl(InvitationRepository repository) {
        this.repository = repository;
    }
    @Override
    public InvitationResponse getInvitation(Long id) {
        Invitation invitation=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Invitation not found with id: " + id));
        return mapEntityToDto(invitation);
    }
    @Override
    public InvitationResponse getInvitationByEmail(String email) {
        Invitation invitation=repository.findByRecipientEmail(email)
                .orElseThrow(()->new RuntimeException("Invitation not found with email: " + email));
        return mapEntityToDto(invitation);
    }
    @Override
    public InvitationResponse getInvitationByFamilyId(Long familyId) {
        Invitation invitation=repository.findByFamilyId(familyId)
                .orElseThrow(()->new RuntimeException("Invitation not found with familyId: " + familyId));
        return mapEntityToDto(invitation);
    }

    @Override
    public void deleteInvitation(Long id) {
        repository.deleteById(id);
    }

    @Override
    public InvitationResponse acceptInvitation(Long id) {
        Invitation invitation=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Invitation not found with id: " + id));
        invitation.setStatus(InvitationStatus.ACCEPTED);
        repository.save(invitation);

        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse declineInvitation(Long id) {
        Invitation invitation=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Invitation not found with id: " + id));
        invitation.setStatus(InvitationStatus.DECLINED);
        repository.save(invitation);

        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse resendInvitation(Long id) {
        Invitation invitation=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Invitation not found with id: " + id));
        //To-Do: Resend email logic here
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse cancelInvitation(Long id) {
        Invitation invitation=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Invitation not found with id: " + id));
        invitation.setStatus(InvitationStatus.CANCELED);
        repository.save(invitation);

        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse sendInvitation(InvitationRequest request) {

        Invitation invitation=mapDtoToEntity(request);
        //To-Do: Send email logic here
        repository.save(invitation);
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse updateInvitation(Long id, InvitationRequest request) {
        Invitation invitation=repository.findById(id)
                .orElseThrow(()->new RuntimeException("Invitation not found with id: " + id));
        if(request.getRecipientEmail()!=null){
            invitation.setRecipientEmail(request.getRecipientEmail());
        }
        if (request.getFamilyId()!=null){
            invitation.setFamilyId(request.getFamilyId());
        }
        if (request.getSenderId()!=null){
            invitation.setSenderId(request.getSenderId());
        }
        repository.save(invitation);
        return mapEntityToDto(invitation);
    }

    private InvitationResponse mapEntityToDto(Invitation invitation) {
        return InvitationResponse.builder()
                .id(invitation.getId())
                .email(invitation.getRecipientEmail())
                .status(invitation.getStatus())
                .respondedAt(invitation.getRespondedAt())
                .build();
    }

    private Invitation mapDtoToEntity(InvitationRequest request) {
        return Invitation.builder()
                .recipientEmail(request.getRecipientEmail())
                .familyId(request.getFamilyId())
                .senderId(request.getSenderId())
                .status(InvitationStatus.PENDING)
                .sentAt(LocalDateTime.now())
                .build();

    }
}
