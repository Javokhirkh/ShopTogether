package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.invitation.InvitationRequest;
import com.example.portfolio.DTOs.invitation.InvitationResponse;
import com.example.portfolio.enums.InvitationStatus;
import com.example.portfolio.models.Family;
import com.example.portfolio.models.Invitation;
import com.example.portfolio.models.User;
import com.example.portfolio.repository.FamilyRepository;
import com.example.portfolio.repository.InvitationRepository;
import com.example.portfolio.repository.UserRepository;
import com.example.portfolio.sevices.serviceInterfaces.InvitationService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public InvitationServiceImpl(InvitationRepository invitationRepository,
                                 UserRepository userRepository,
                                 FamilyRepository familyRepository) {
        this.invitationRepository = invitationRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    @Override
    public InvitationResponse get(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse getByEmail(String email) {
        Invitation invitation = invitationRepository.findByRecipientEmail(email)
                .orElseThrow(() -> new RuntimeException("Invitation not found with email: " + email));
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse getByFamilyId(Long familyId) {
        Invitation invitation = invitationRepository.findByFamilyId(familyId)
                .orElseThrow(() -> new RuntimeException("Invitation not found with familyId: " + familyId));
        return mapEntityToDto(invitation);
    }

    @Override
    public void delete(Long id) {
        invitationRepository.deleteById(id);
    }

    @Override
    public InvitationResponse accept(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        invitation.setStatus(InvitationStatus.ACCEPTED);
        invitation.setRespondedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse decline(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        invitation.setStatus(InvitationStatus.DECLINED);
        invitation.setRespondedAt(LocalDateTime.now());
        invitationRepository.save(invitation);
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse resend(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        // TODO: Add email sending logic here
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse cancel(Long id) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));
        invitation.setStatus(InvitationStatus.CANCELED);
        invitationRepository.save(invitation);
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse send(InvitationRequest request) {
        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender not found with id: " + request.getSenderId()));

        Family family = familyRepository.findById(request.getFamilyId())
                .orElseThrow(() -> new RuntimeException("Family not found with id: " + request.getFamilyId()));

        Invitation invitation = Invitation.builder()
                .sender(sender)
                .family(family)
                .recipientEmail(request.getRecipientEmail())
                .status(InvitationStatus.PENDING)
                .sentAt(LocalDateTime.now())
                .build();

        invitationRepository.save(invitation);
        return mapEntityToDto(invitation);
    }

    @Override
    public InvitationResponse update(Long id, InvitationRequest request) {
        Invitation invitation = invitationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invitation not found with id: " + id));

        if (request.getRecipientEmail() != null) {
            invitation.setRecipientEmail(request.getRecipientEmail());
        }

        if (request.getSenderId() != null) {
            User sender = userRepository.findById(request.getSenderId())
                    .orElseThrow(() -> new RuntimeException("Sender not found with id: " + request.getSenderId()));
            invitation.setSender(sender);
        }

        if (request.getFamilyId() != null) {
            Family family = familyRepository.findById(request.getFamilyId())
                    .orElseThrow(() -> new RuntimeException("Family not found with id: " + request.getFamilyId()));
            invitation.setFamily(family);
        }

        invitationRepository.save(invitation);
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
}
