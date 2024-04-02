package com.traveloper.tourfinder.oauth2.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Builder
public class SocialProviderAccessTokenRequestDto {
    String code;
    String socialTokenUri;
    String grantType;
    String clientId;
    String clientSecret;
    String redirectUri;
}
