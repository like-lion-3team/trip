package com.traveloper.tourfinder.auth.controller;

import com.traveloper.tourfinder.auth.dto.Token.ReissuanceDto;
import com.traveloper.tourfinder.auth.service.TokenService;
import com.traveloper.tourfinder.common.util.SecurityContextUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Token", description = "토큰과 관련된 API")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;
    @PostMapping("/token")
    public void reissuanceAccessToken(
            @RequestBody
            ReissuanceDto dto
    ){
        // TODO: AccessToken 재발급
        Optional<String> token = tokenService.rolling(dto);

        token.ifPresent(s -> System.out.printf(s + "토큰"));
    }

    @GetMapping("/destroy")
    public void destroyToken(){
        // TODO: AccessToken, RefreshToken 삭제
    }
}
