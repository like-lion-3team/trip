package com.traveloper.tourfinder.auth.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * <p>순환참조를 방지하기 위해 해당 서비스는 다른 서비스를 절대 참조하지 않아야 합니다.</p>
 */


@Service
public class TokenService {


    public void rolling(
            UUID uuid
    ) {
        // TODO: uuid를 기반으로 유저를 검색하고 토큰을 재생성 합니다.
        // TODO: AccessToken을 발급하고, 기존 RefreshToken을 삭제하고 재생성 합니다.
        // TODO: Redis에 AccessToken ( key ) : RefreshToken ( value ) 형태로 저장합니다.
    }

    public void destroy(
            UUID uuid
    ) {
        // TODO: 유저의 인증정보 파기
    }


}
