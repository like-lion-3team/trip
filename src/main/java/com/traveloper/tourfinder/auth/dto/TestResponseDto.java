package com.traveloper.tourfinder.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestResponseDto {
    @Schema(description = "User Account", example = "userAccount@gmail.com")
    private String account;
    @Schema(description = "User Name", example = "userName@gmail.com")
    private String name;
}
