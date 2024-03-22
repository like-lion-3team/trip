package com.traveloper.tourfinder.common;


import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@AllArgsConstructor
public class RedisRepo {
    private RedisTemplate<String, String> redisTemplate;

    /**
     * <p>리프래시 토큰을 Redis에 저장할 때 사용합니다.</p>
     * @param key AccessToken을 key로 합니다
     * @param value RefreshToken을 value로 합니다.
     * */
    public void saveRefreshToken(String key, String value) {
        redisTemplate.opsForValue().set(key, value,AppConstants.REFRESH_TOKEN_EXPIRE_SECOND, TimeUnit.SECONDS);
    }

    /**
     * <p>Redis에 저장된 리프레시 토큰을 조회할 때 사용합니다.</p>
     * @param key AccessToken
     * @return  null or String ( RefreshToken )
     * */
    public Optional<String> getRefreshToken(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return Optional.ofNullable(value).map(String::valueOf);
    }

    /**
     * <p>RefreshToken을 삭제하는 메서드 입니다.</p>
     * @param key AccessToken
     * @return boolean - 삭제 성공시 true 실패시 false를 리턴합니다.
     * */
    public boolean destroyRefreshToken(String key) {
        redisTemplate.delete(key);
        return getRefreshToken(key).isEmpty();
    }

    /**
     * <p>이메일 인증 코드를 조회하는 메서드 입니다.</p>
     * @param email 인증에 사용했던 이메일
     * @return null or String ( verifyCode )
     * */
    public Optional<String> getVerifyCode(String email){
        Object value = redisTemplate.opsForValue().get(email);
        return Optional.ofNullable(value).map(String::valueOf);
    }

    /**
     * <p>이메일과 인증 코드를 저장하는 메서드 입니다.</p>
     *
     * @param email 인증에 사용했던 이메일
     * @param code  이메일로 전송한 인증코드
     * @return
     */
    public String saveVerifyCode(String email, String code){
        redisTemplate.opsForValue().set(email, code,AppConstants.EMAIL_VERIFY_CODE_EXPIRE_SECOND, TimeUnit.SECONDS);
        return email;
    }
}
