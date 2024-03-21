package com.traveloper.tourfinder.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignInDto {
    @Schema(example = "test@test.test")
    private String email;
    @Schema(example = "test123")
    private String password;
}
