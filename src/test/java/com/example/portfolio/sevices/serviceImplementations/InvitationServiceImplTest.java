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
import com.example.portfolio.sevices.serviceImplementations.InvitationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InvitationServiceImplTest {

    private InvitationRepository invitationRepository;
    private UserRepository userRepository;
    private FamilyRepository familyRepository;

    private InvitationServiceImpl service;

    @BeforeEach
    void setUp() {
        invitationRepository = mock(InvitationRepository.class);
        userRepository = mock(UserRepository.class);
        familyRepository = mock(FamilyRepository.class);
        service = new InvitationServiceImpl(invitationRepository, userRepository, familyRepository);
    }

    @Test
    void get_success() {
        Invitation inv = Invitation.builder()
                .id(1L)
                .recipientEmail("a@b.com")
                .status(InvitationStatus.PENDING)
                .respondedAt(null)
                .build();

        when(invitationRepository.findById(1L)).thenReturn(Optional.of(inv));

        InvitationResponse resp = service.get(1L);

        assertEquals(1L, resp.getId());
        assertEquals("a@b.com", resp.getEmail());
        assertEquals(InvitationStatus.PENDING, resp.getStatus());
        assertNull(resp.getRespondedAt());
    }

    @Test
    void get_notFound() {
        when(invitationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.get(1L));
    }

    @Test
    void getByEmail_success() {
        Invitation inv = Invitation.builder()
                .id(2L)
                .recipientEmail("x@y.com")
                .status(InvitationStatus.PENDING)
                .build();

        when(invitationRepository.findByRecipientEmail("x@y.com")).thenReturn(Optional.of(inv));

        InvitationResponse resp = service.getByEmail("x@y.com");

        assertEquals(2L, resp.getId());
        assertEquals("x@y.com", resp.getEmail());
    }

    @Test
    void getByEmail_notFound() {
        when(invitationRepository.findByRecipientEmail("no@mail")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getByEmail("no@mail"));
    }

    @Test
    void getByFamilyId_success() {
        Invitation inv = Invitation.builder()
                .id(3L)
                .recipientEmail("fam@mail")
                .status(InvitationStatus.PENDING)
                .build();

        when(invitationRepository.findByFamilyId(10L)).thenReturn(Optional.of(inv));

        InvitationResponse resp = service.getByFamilyId(10L);

        assertEquals(3L, resp.getId());
        assertEquals("fam@mail", resp.getEmail());
    }

    @Test
    void getByFamilyId_notFound() {
        when(invitationRepository.findByFamilyId(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getByFamilyId(99L));
    }

    @Test
    void delete_callsRepository() {
        service.delete(5L);
        verify(invitationRepository).deleteById(5L);
    }

    @Test
    void accept_setsStatusAndRespondedAt_andSaves() {
        Invitation inv = Invitation.builder()
                .id(6L)
                .recipientEmail("acc@mail")
                .status(InvitationStatus.PENDING)
                .build();

        when(invitationRepository.findById(6L)).thenReturn(Optional.of(inv));
        when(invitationRepository.save(any(Invitation.class))).thenAnswer(i -> i.getArgument(0));

        InvitationResponse resp = service.accept(6L);

        assertEquals(6L, resp.getId());
        assertEquals(InvitationStatus.ACCEPTED, resp.getStatus());
        assertNotNull(resp.getRespondedAt());

        ArgumentCaptor<Invitation> captor = ArgumentCaptor.forClass(Invitation.class);
        verify(invitationRepository).save(captor.capture());
        assertEquals(InvitationStatus.ACCEPTED, captor.getValue().getStatus());
        assertNotNull(captor.getValue().getRespondedAt());
    }

    @Test
    void decline_setsStatusAndRespondedAt_andSaves() {
        Invitation inv = Invitation.builder()
                .id(7L)
                .recipientEmail("dec@mail")
                .status(InvitationStatus.PENDING)
                .build();

        when(invitationRepository.findById(7L)).thenReturn(Optional.of(inv));
        when(invitationRepository.save(any(Invitation.class))).thenAnswer(i -> i.getArgument(0));

        InvitationResponse resp = service.decline(7L);

        assertEquals(7L, resp.getId());
        assertEquals(InvitationStatus.DECLINED, resp.getStatus());
        assertNotNull(resp.getRespondedAt());

        ArgumentCaptor<Invitation> captor = ArgumentCaptor.forClass(Invitation.class);
        verify(invitationRepository).save(captor.capture());
        assertEquals(InvitationStatus.DECLINED, captor.getValue().getStatus());
        assertNotNull(captor.getValue().getRespondedAt());
    }

    @Test
    void resend_returnsMappedInvitation_withoutSaving() {
        Invitation inv = Invitation.builder()
                .id(8L)
                .recipientEmail("resend@mail")
                .status(InvitationStatus.PENDING)
                .build();

        when(invitationRepository.findById(8L)).thenReturn(Optional.of(inv));

        InvitationResponse resp = service.resend(8L);

        assertEquals(8L, resp.getId());
        assertEquals("resend@mail", resp.getEmail());
        verify(invitationRepository, never()).save(any());
    }

    @Test
    void cancel_setsCanceledAndSaves() {
        Invitation inv = Invitation.builder()
                .id(9L)
                .recipientEmail("can@mail")
                .status(InvitationStatus.PENDING)
                .build();

        when(invitationRepository.findById(9L)).thenReturn(Optional.of(inv));
        when(invitationRepository.save(any(Invitation.class))).thenAnswer(i -> i.getArgument(0));

        InvitationResponse resp = service.cancel(9L);

        assertEquals(9L, resp.getId());
        assertEquals(InvitationStatus.CANCELED, resp.getStatus());

        ArgumentCaptor<Invitation> captor = ArgumentCaptor.forClass(Invitation.class);
        verify(invitationRepository).save(captor.capture());
        assertEquals(InvitationStatus.CANCELED, captor.getValue().getStatus());
    }

    @Test
    void send_createsInvitation_andSaves() {
        User sender = User.builder().id(11L).username("s").build();
        Family family = Family.builder().id(22L).name("Fam").build();

        InvitationRequest req = new InvitationRequest();
        req.setSenderId(11L);
        req.setFamilyId(22L);
        req.setRecipientEmail("new@mail");

        Invitation saved = Invitation.builder()
                .id(12L)
                .recipientEmail("new@mail")
                .status(InvitationStatus.PENDING)
                .sender(sender)
                .family(family)
                .sentAt(LocalDateTime.now())
                .build();

        when(userRepository.findById(11L)).thenReturn(Optional.of(sender));
        when(familyRepository.findById(22L)).thenReturn(Optional.of(family));
        when(invitationRepository.save(any(Invitation.class))).thenReturn(saved);

        InvitationResponse resp = service.send(req);

        assertEquals(12L, resp.getId());
        assertEquals("new@mail", resp.getEmail());
        assertEquals(InvitationStatus.PENDING, resp.getStatus());
        verify(invitationRepository).save(any(Invitation.class));
    }

    @Test
    void send_senderNotFound_throws() {
        InvitationRequest req = new InvitationRequest();
        req.setSenderId(100L);
        req.setFamilyId(1L);
        req.setRecipientEmail("x@mail");

        when(userRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.send(req));
    }

    @Test
    void send_familyNotFound_throws() {
        User sender = User.builder().id(13L).build();
        InvitationRequest req = new InvitationRequest();
        req.setSenderId(13L);
        req.setFamilyId(999L);
        req.setRecipientEmail("x@mail");

        when(userRepository.findById(13L)).thenReturn(Optional.of(sender));
        when(familyRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.send(req));
    }

    @Test
    void update_updatesFields_andSaves() {
        User oldSender = User.builder().id(20L).build();
        Family oldFamily = Family.builder().id(30L).build();

        Invitation existing = Invitation.builder()
                .id(21L)
                .recipientEmail("old@mail")
                .sender(oldSender)
                .family(oldFamily)
                .status(InvitationStatus.PENDING)
                .build();

        User newSender = User.builder().id(40L).build();
        Family newFamily = Family.builder().id(50L).build();

        InvitationRequest req = new InvitationRequest();
        req.setRecipientEmail("new@mail");
        req.setSenderId(40L);
        req.setFamilyId(50L);

        when(invitationRepository.findById(21L)).thenReturn(Optional.of(existing));
        when(userRepository.findById(40L)).thenReturn(Optional.of(newSender));
        when(familyRepository.findById(50L)).thenReturn(Optional.of(newFamily));
        when(invitationRepository.save(any(Invitation.class))).thenAnswer(i -> i.getArgument(0));

        InvitationResponse resp = service.update(21L, req);

        assertEquals(21L, resp.getId());
        assertEquals("new@mail", resp.getEmail());

        ArgumentCaptor<Invitation> captor = ArgumentCaptor.forClass(Invitation.class);
        verify(invitationRepository).save(captor.capture());
        Invitation saved = captor.getValue();
        assertEquals("new@mail", saved.getRecipientEmail());
        assertEquals(40L, saved.getSender().getId());
        assertEquals(50L, saved.getFamily().getId());
    }

    @Test
    void update_notFound_throws() {
        when(invitationRepository.findById(999L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.update(999L, new InvitationRequest()));
    }
}
