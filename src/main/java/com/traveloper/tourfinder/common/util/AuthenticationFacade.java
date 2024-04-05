package com.traveloper.tourfinder.common.util;

import com.traveloper.tourfinder.auth.entity.CustomUserDetails;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    public Member getCurrentMember() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof CustomUserDetails userDetails) {
            return userDetails.getMember();
        } else {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.AUTHENTICATION_FAILED);
        }
    }
}
