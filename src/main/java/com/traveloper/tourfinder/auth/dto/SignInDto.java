package com.traveloper.tourfinder.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class SignInDto {
    @Schema(example = "gallowaydaniel@jones.biz")
    private String email;
    @Schema(example = "admin123")
    private String password;
}
