package com.traveloper.tourfinder.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryRequestDto {
    private String email;
    private String newPassword;
    private String code;
}
