package com.traveloper.tourfinder.auth.service;

import org.springframework.stereotype.Service;

@Service
public class SocialService {
    /**
     * 소셜 로그인 기능구현시 주의사항
     *
     * 1. 소셜 로그인 후 가져온 이메일이 이미 DB에 있다면 단순히 연동처리
     * 2. 소셜 로그인 연동 회원은 비밀번호 변경을 할 수 없습니다. ( 소셜 사업자 쪽에서 비밀번호 인증을 처리하기 때문에 비번 변경 비활성화 )
     * 3. 마이페이지 비밀번호 변경 버튼 및 기능 비활성화
     * 4. 비밀번호 분실시 비밀번호 초기화를 하는데 소셜로그인 연동시 이 기능도 사용할 수 없도록 처리
     * 5. 어떤 소셜 로그인을 연동했는지 마이페이지 등 특정 부분에 표시
     * */

    public void kakaoLogin(){
        // TODO: 카카오 로그인 기능 구현
        // TODO: 회원가입시 받은 이메일을 토대로 MemberRepository 조회
        // TODO: 존재하는 유저라면 연동처리
    }

    public void naverLogin(){
        // TODO: 네이버 로그인 기능 구현
        // TODO: 회원가입시 받은 이메일을 토대로 MemberRepository 조회
        // TODO: 존재하는 유저라면 연동처리
    }
}
