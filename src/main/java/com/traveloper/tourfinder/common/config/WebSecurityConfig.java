package com.traveloper.tourfinder.common.config;

import com.traveloper.tourfinder.auth.jwt.JwtTokenFilter;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "api/v1/auth/**",
                                "api-test/**"
                        )
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/*")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api-docs/*")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api-docs")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).addFilterBefore(
                        new JwtTokenFilter(jwtTokenUtils, memberService),
                        AuthorizationFilter.class
                );

        return http.build();
    }
}

