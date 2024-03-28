package com.traveloper.tourfinder.common.config;

import com.traveloper.tourfinder.auth.jwt.JwtTokenFilter;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.service.MemberService;
import com.traveloper.tourfinder.common.exception.CustomJwtExceptionFilter;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final PermitAllPath permitAllPath;
    private final MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                // cors - vscode 로컬 라이브 서버 포트 허용
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:5500"));
                        config.setAllowedOrigins(Collections.singletonList("http://127.0.0.1:5500"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L); //1시간
                        return config;
                    }
                }))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(permitAllPath.getPERMIT_ALL_PATTERN())
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                ).addFilterBefore(
                        new JwtTokenFilter(jwtTokenUtils, memberService),
                        AuthorizationFilter.class
                )
                .addFilterBefore(
                        new CustomJwtExceptionFilter(jwtTokenUtils,permitAllPath), AuthorizationFilter.class
                );

        return http.build();
    }
}

