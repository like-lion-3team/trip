package com.traveloper.tourfinder.auth.dto.Token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReissuanceDto {
    private String accessToken;
    private String uuid;
}
