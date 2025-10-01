package com.example.portfolio.DTOs.invitation;

import com.example.portfolio.enums.InvitationStatus;
import com.example.portfolio.models.Family;
import com.example.portfolio.models.User;
import lombok.Data;

@Data
public class InvitationResponse {
    private Long id;
    private String email;
    private Family family;
    private User sender;
    private InvitationStatus status;
}
