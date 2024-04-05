package com.traveloper.tourfinder.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMemberDto {
    @Schema(example = "test")
    private String nickname;
    @Schema(example = "test@test.test")
    private String email;
    @Schema(example = "test123")
    private String password;
}

