package com.traveloper.tourfinder.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryVerifyCodeRequestDto {
    private String email;
    private String code;
}
