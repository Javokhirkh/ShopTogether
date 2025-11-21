package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.familyMember.FamilyMemberRequest;
import com.example.portfolio.DTOs.familyMember.FamilyMemberResponse;
import com.example.portfolio.enums.FamilyRole;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.Family;
import com.example.portfolio.models.FamilyMember;
import com.example.portfolio.models.User;
import com.example.portfolio.repository.FamilyMemberRepository;
import com.example.portfolio.repository.FamilyRepository;
import com.example.portfolio.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FamilyMemberServiceImplTest {

    private FamilyMemberRepository memberRepository;
    private FamilyRepository familyRepository;
    private UserRepository userRepository;

    private FamilyMemberServiceImpl service;

    @BeforeEach
    void setUp() {
        memberRepository = mock(FamilyMemberRepository.class);
        familyRepository = mock(FamilyRepository.class);
        userRepository = mock(UserRepository.class);
        service = new FamilyMemberServiceImpl(memberRepository, familyRepository, userRepository);
    }

    @Test
    void addMember_success() {
        Long familyId = 1L;
        Long userId = 2L;

        Family family = Family.builder().id(familyId).name("Family A").build();
        User user = User.builder().id(userId).username("john").build();

        FamilyMemberRequest request = new FamilyMemberRequest();
        request.setUserId(userId);
        request.setRole(FamilyRole.ADMIN);

        FamilyMember saved = FamilyMember.builder()
                .id(10L)
                .family(family)
                .user(user)
                .role(FamilyRole.ADMIN)
                .joinedAt(LocalDateTime.now())
                .build();

        when(familyRepository.findById(familyId)).thenReturn(Optional.of(family));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.save(any(FamilyMember.class))).thenReturn(saved);

        FamilyMemberResponse response = service.addMember(familyId, request);

        assertEquals(10L, response.getId());
        assertEquals(userId, response.getUserId());
        assertEquals("john", response.getUsername());
        assertEquals(familyId, response.getFamilyId());
        assertEquals("Family A", response.getFamilyName());
        assertEquals(FamilyRole.ADMIN, response.getRole());

        verify(memberRepository, times(1)).save(any(FamilyMember.class));
    }

    @Test
    void addMember_familyNotFound() {
        when(familyRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> service.addMember(1L, new FamilyMemberRequest()));
    }

    @Test
    void addMember_userNotFound() {
        Family family = Family.builder().id(1L).build();
        FamilyMemberRequest request = new FamilyMemberRequest();
        request.setUserId(2L);

        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addMember(1L, request));
    }

    @Test
    void getMembers_success() {
        Family family = Family.builder().id(1L).name("Test").build();
        User user = User.builder().id(2L).username("alex").build();

        FamilyMember m = FamilyMember.builder()
                .id(5L)
                .family(family)
                .user(user)
                .role(FamilyRole.MEMBER)
                .joinedAt(LocalDateTime.now())
                .build();

        when(memberRepository.findAllByFamilyId(1L)).thenReturn(List.of(m));

        List<FamilyMemberResponse> members = service.getMembers(1L);

        assertEquals(1, members.size());
        assertEquals(5L, members.get(0).getId());
        assertEquals("alex", members.get(0).getUsername());
        assertEquals(FamilyRole.MEMBER, members.get(0).getRole());
    }

    @Test
    void removeMember_success() {
        Long familyId = 1L;
        Long userId = 2L;

        Family family = Family.builder().id(familyId).build();
        User user = User.builder().id(userId).build();

        FamilyMember member = FamilyMember.builder().id(99L).family(family).user(user).build();

        when(familyRepository.findById(familyId)).thenReturn(Optional.of(family));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(memberRepository.findByFamilyAndUser(family, user)).thenReturn(Optional.of(member));

        service.removeMember(familyId, userId);

        verify(memberRepository, times(1)).delete(member);
    }

    @Test
    void removeMember_memberNotFound() {
        Family family = Family.builder().id(1L).build();
        User user = User.builder().id(2L).build();

        when(familyRepository.findById(1L)).thenReturn(Optional.of(family));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(memberRepository.findByFamilyAndUser(family, user)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.removeMember(1L, 2L));
    }
}
