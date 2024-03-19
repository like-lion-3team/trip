package com.traveloper.tourfinder.auth.controller;

import com.traveloper.tourfinder.auth.dto.FailTestDto;
import com.traveloper.tourfinder.auth.dto.TestDto;
import com.traveloper.tourfinder.auth.dto.TestResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @GetMapping("api01")
    @Parameter(name = "name", description = "param 설명")
    @Parameter(name = "value", description = "param 설명")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공시 받는 응답", content = {@Content(schema = @Schema(implementation = TestResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "요청 실패시 받는 응답", content = {@Content(schema = @Schema(implementation = FailTestDto.class))}),
    })
    public ResponseEntity<TestResponseDto> api01(
            String name,
            String value
    ) {
        return ResponseEntity.ok(
                TestResponseDto.builder()
                        .account("Test")
                        .name("Test User")
                        .build()
        );
    }

    @PostMapping("api02")
    @Operation(summary = "엔드포인트 제목", description = "엔드포인트 설명")
    public void api02(
            @RequestBody
            TestDto dto
    ) {

    }
}
