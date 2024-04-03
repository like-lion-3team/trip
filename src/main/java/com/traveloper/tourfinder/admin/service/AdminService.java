package com.traveloper.tourfinder.admin.service;

import com.traveloper.tourfinder.admin.repo.AdminMemberRepo;
import com.traveloper.tourfinder.auth.dto.SignInDto;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.entity.Role;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.repo.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AdminService {
    private final AdminMemberRepo adminMemberRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtTokenUtils jwtTokenUtils;
    public TokenDto signIn(SignInDto dto){
        Member member = adminMemberRepo.findMemberByEmail(dto.getEmail()).orElseThrow(
                () -> new EntityNotFoundException("로그인 실패")
        );

        if(!passwordEncoder.matches(dto.getPassword(), member.getPassword())){
            throw new AccessDeniedException("로그인 실패");
        }

        // 권한 체크
        Role role = roleRepository.findRoleByName("SERVICE_ADMIN").orElseThrow(
                () -> new AccessDeniedException("Role을 찾을 수 없습니다.")
        );
        if(!role.getName().equals(member.getRole().getName())){
            new AccessDeniedException("로그인 실패");
        }

        String token = jwtTokenUtils.generateToken(member);



        return TokenDto.builder()
                .accessToken(token)
                .expiredDate(LocalDateTime.now().plusSeconds(60 * 60))
                .expiredSecond(60 * 60)
                .build();
    }
}
