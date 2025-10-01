package com.example.portfolio.DTOs.invitation;

import lombok.Data;

@Data
public class InvitationRequest {
    private String recipientEmail;
    private Long familyId;
    private Long senderId;



}
