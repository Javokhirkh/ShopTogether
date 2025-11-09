package com.example.portfolio.sevices.serviceImplementations;

import com.example.portfolio.DTOs.familyMember.FamilyMemberRequest;
import com.example.portfolio.DTOs.familyMember.FamilyMemberResponse;
import com.example.portfolio.exceptions.ResourceNotFoundException;
import com.example.portfolio.models.Family;
import com.example.portfolio.models.FamilyMember;
import com.example.portfolio.models.User;
import com.example.portfolio.repository.FamilyMemberRepository;
import com.example.portfolio.repository.FamilyRepository;
import com.example.portfolio.repository.UserRepository;
import com.example.portfolio.sevices.serviceInterfaces.FamilyMemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {
    private final FamilyMemberRepository memberRepository;
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;

    public FamilyMemberServiceImpl(FamilyMemberRepository memberRepository, FamilyRepository familyRepository,
                                   UserRepository userRepository) {
        this.memberRepository = memberRepository;
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public FamilyMemberResponse addMember(Long familyId, FamilyMemberRequest request) {
        Family family =getFamilyById(familyId);
        User user = getUserById(request.getUserId());
        FamilyMember member = FamilyMember.builder()
                .family(family)
                .user(user)
                .role(request.getRole())
                .joinedAt(LocalDateTime.now())
                .build();

        FamilyMember saved = memberRepository.save(member);
        return mapToDto(saved);
    }

    @Override
    public List<FamilyMemberResponse> getMembers(Long familyId) {
        return memberRepository.findAllByFamilyId(familyId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void removeMember(Long familyId, Long userId) {
        Family family=getFamilyById(familyId);
        User user=getUserById(userId);
        FamilyMember member = memberRepository.findByFamilyAndUser(family, user)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        memberRepository.delete(member);
    }

    private FamilyMemberResponse mapToDto(FamilyMember member) {
        return FamilyMemberResponse.builder()
                .id(member.getId())
                .userId(member.getUser().getId())
                .username(member.getUser().getUsername())
                .familyId(member.getFamily().getId())
                .familyName(member.getFamily().getName())
                .role(member.getRole())
                .joinedAt(member.getJoinedAt())
                .build();
    }
    private Family getFamilyById(Long familyId) {
        return familyRepository.findById(familyId)
                .orElseThrow(() -> new ResourceNotFoundException("Family not found with id: " + familyId));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User not found with id: " + userId));
    }
}
