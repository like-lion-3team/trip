package com.traveloper.tourfinder.auth.service;

import com.traveloper.tourfinder.auth.dto.Token.ReissuanceDto;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import com.traveloper.tourfinder.common.AppConstants;
import com.traveloper.tourfinder.common.RedisRepo;
import com.traveloper.tourfinder.common.exception.CustomGlobalErrorCode;
import com.traveloper.tourfinder.common.exception.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * <p>순환참조를 방지하기 위해 해당 서비스는 다른 서비스를 절대 참조하지 않아야 합니다.</p>
 */


@Service
@RequiredArgsConstructor
public class TokenService {
    private final RedisRepo redisRepo;
    private final JwtTokenUtils jwtTokenUtils;
    private final MemberRepository memberRepository;

    public Optional<String> rolling(ReissuanceDto dto) {
        Optional<String> storedRefreshToken = redisRepo.getRefreshToken(dto.getAccessToken());

        if (storedRefreshToken.isEmpty()) {
            return Optional.empty(); // Redis에 토큰이 없다면 빈 Optional 반환
        }

        System.out.println(storedRefreshToken.get() + "토큰 체크");

        Member member = memberRepository.findMemberByUuid(dto.getUuid())
                .orElseThrow(() -> new GlobalExceptionHandler(CustomGlobalErrorCode.NOT_FOUND_MEMBER));

        // 새로운 토큰 생성
        String newAccessToken = jwtTokenUtils.generateToken(member,AppConstants.ACCESS_TOKEN_EXPIRE_SECOND);
        String newRefreshToken = jwtTokenUtils.generateToken(member, AppConstants.REFRESH_TOKEN_EXPIRE_SECOND);

        // 기존 리프레시 토큰 삭제
        redisRepo.destroyRefreshToken(dto.getAccessToken());

        // 새로운 토큰 Redis에 저장
        redisRepo.saveRefreshToken(newAccessToken, newRefreshToken);

        // 새로운 액세스 토큰 반환
        return Optional.of(newAccessToken);
    }

    public void destroy(
            UUID uuid
    ) {
        // TODO: 유저의 인증정보 파기
    }


}
