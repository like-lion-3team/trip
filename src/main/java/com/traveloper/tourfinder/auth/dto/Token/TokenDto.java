package com.traveloper.tourfinder.auth.dto.Token;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@Builder
public class TokenDto {
    // 엑세스 토큰
    private String accessToken;

    // 만료일
    private LocalDateTime expiredDate;

    // 토큰 만료 시간 ( second )
    private Integer expiredSecond;
}