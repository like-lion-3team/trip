package com.traveloper.tourfinder.auth.controller;

import com.traveloper.tourfinder.auth.dto.Token.ReissuanceDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.service.TokenService;
import com.traveloper.tourfinder.common.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(name = "Token", description = "토큰과 관련된 API")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<TokenDto> reissuanceAccessToken(
            @RequestBody
            ReissuanceDto dto
    ) {
        // TODO: AccessToken 재발급
        String token = tokenService.rolling(dto).orElseThrow(
                () -> new RuntimeException("토큰 재발급 오류")
        );


        TokenDto response = TokenDto.builder()
                .accessToken(token)
                .expiredDate(LocalDateTime.now().plusSeconds(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND))
                .expiredSecond(AppConstants.ACCESS_TOKEN_EXPIRE_SECOND)
                .build();
        return ResponseEntity.ok(response);

    }


    @GetMapping("/destroy")
    public void destroyToken(){
        // TODO: AccessToken, RefreshToken 삭제
    }
}
