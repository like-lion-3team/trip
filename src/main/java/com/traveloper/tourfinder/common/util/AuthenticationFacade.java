package com.traveloper.tourfinder.common.util;

import com.traveloper.tourfinder.auth.entity.CustomUserDetails;
import com.traveloper.tourfinder.auth.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails)  authentication.getPrincipal();
        return userDetails.getMember();
    }
}
