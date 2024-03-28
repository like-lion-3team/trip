package com.traveloper.tourfinder.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordReq {
    @NotBlank
    private String currentPassword;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$]).{8,20}$",
    message = "8글자 이상 20글자 이하, 특수 문자 !@#$ 중 1개 이상 포함, 영어 대문자 포함")
    private String newPassword;
}
