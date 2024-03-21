package com.traveloper.tourfinder.auth.dto.Token;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissuanceDto {
    private String accessToken;
    private String uuid;
}
