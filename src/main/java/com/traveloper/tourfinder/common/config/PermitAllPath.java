package com.traveloper.tourfinder.common.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Getter
@Component
public class PermitAllPath {
    String[] PERMIT_ALL_PATTERN= {
            "/api/v1/auth/sign-in",
            "/api/v1/auth/sign-up",
            "/api-test/**",
            "/api/v1/courses/**",
            "/api/v1/places/**",

            // html
            "/admin/**",
            "/css/**",
            "/js/**",
            "/*.ico",
            "/*.jpg",
            "/*.jpeg",
            "/images/**",
            "/img/articles/**",




            "/login","/sign-up","/my-page", "/home",


            // board
            "/board",
            "/board/articles",
            "/board/articles/{articleId}",
            "/board/articles/create",
            "/board/articles/{articleId}/update",
            "/api/v1/auth/current-member",
            "/api/v1/articles/{articleId}/like",
            "/api/v1/articles/{articleId}",


            "/password-change",


            "/swagger-ui/*",
            "/v1/api-docs/*",
            "/v1/api-docs",

            // social
            "/oauth2/callback",
            "/api/v1/oauth2/**",

            "/api/v1/auth/password-recovery",
            "/api/v1/auth/password-recovery/verify-code",
            "/api/v1/auth/duplicate-check/**",
            "/api/v1/auth/send/**",
            "/api/v1/auth/email/*/verify-code/*"

    };
}
