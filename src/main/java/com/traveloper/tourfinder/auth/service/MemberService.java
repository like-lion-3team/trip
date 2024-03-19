package com.traveloper.tourfinder.auth.service;


import com.traveloper.tourfinder.auth.dto.TokenDto;
import com.traveloper.tourfinder.auth.entity.CustomUserDetails;
import com.traveloper.tourfinder.auth.entity.Member;
import com.traveloper.tourfinder.auth.jwt.JwtTokenUtils;
import com.traveloper.tourfinder.auth.repo.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final JwtTokenUtils jwtTokenUtils;

    /**
     * @param nickname 유저가 입력한 닉네임
     * @param email    유저가 입력한 이메일
     * @param password 유저가 입력한 비밀번호
     */
    public void signup(
            String nickname,
            String email,
            String password
    ) {
        // TODO: 회원가입 - 닉네임, 이메일, 비밀번호 입력받기
        // TODO: 회원가입 - 닉네임 중복체크, 이메일 중복체크
        // TODO: 회원가입 - 비밀번호 저장시 암호화
    }

    public TokenDto login(
            String email,
            String password
    ) {

        Member member = memberRepository.findMemberByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("로그인 실패")
        );
        // TODO: 로그인 - 비밀번호 검증
        // TODO: 비밀번호 해쉬처리한 값과 DB값 비교
        String hashedPassword = password;
        if(!member.getPassword().equals(hashedPassword)){
            throw new AccessDeniedException("로그인 실패");
        }

        String token = jwtTokenUtils.generateToken(member);

        return TokenDto.builder()
                .accessToken(token)
                .expiredDate(LocalDateTime.now().plusSeconds(60 * 60))
                .expiredSecond(60 * 60)
                .build();
    }

    /**
     * <p>이메일 인증용 코드 전송</p>
     */
    public void sendCode(
            String email
    ) {
        // TODO: 이메일 인증 - 받아온 이메일로 인증코드 전송
        // TODO: 이메일 인증 - Redis에 이메일 (key) : 코드 (value)  형태로 값 저장
    }

    public void verifyCode(
            String email,
            String code
    ) {
        // TODO: 인증 코드 검증 - 유저가 입력한 코드 검사
        // TODO: 인증 코드 검증 - Redis에 저장된 값 ( 이메일 )을 검색
        // TODO: 인증 코드 검증 - 일치하면 200 응
    }

    public boolean isPossibleSendCode(
            String email
    ) {
        // TODO ( optional ) : 이메일 인증 - 당일 요청 가능한 횟수 제한 체크
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findMemberByUuid(username);
        if (optionalMember.isEmpty()) throw new UsernameNotFoundException("uuid not found");
        Member member = optionalMember.get();

        return CustomUserDetails.builder()
                .member(member)
                .build();
    }
}

