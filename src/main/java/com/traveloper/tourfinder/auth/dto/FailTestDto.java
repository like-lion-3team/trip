package com.traveloper.tourfinder.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FailTestDto {
    @Schema(description = "실패시 응답", example = "Request Failed")
    private String message;
}
