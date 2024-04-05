package com.traveloper.tourfinder.common;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.traveloper.tourfinder.auth.dto.Token.TokenDto;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Repository
@AllArgsConstructor
public class RedisRepo {
    private RedisTemplate<String, String> redisTemplate;
    private RedisTemplate<String, TokenDto> redisOauthAuthorizeTokenTemplate;
    private ObjectMapper objectMapper;

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


    /**
     * <p>Oauth2 인증 시 임시 토큰 발급하는 메서드 입니다 <br />
     * 임시 토큰이여서 유출되어도 문제는 없지만 인증 완료 후에는 반드시 삭제 해주세요.
     * </p>
     * @param uuid  임시 토큰 조회 시 사용할 랜덤 key 입니다.
     * @param tokenDto 로그인시 전달하는 토큰 Dto 입니다.
     *
     * */
    public String saveOauth2AuthorizeToken(UUID uuid, TokenDto tokenDto){
        redisOauthAuthorizeTokenTemplate.opsForValue().set(uuid.toString(), tokenDto, AppConstants.OAUTH2_AUTHORIZE_TOKEN_EXPIRE_SECOND, TimeUnit.SECONDS);
        return uuid.toString();
    }

    /**
     * <p>임시토큰 삭제 메서드 입니다.</p>
     * @param uuid 토큰 조회시 사용한 key입니다.
     * */
    public boolean destroyOauth2AuthorizeToken(UUID uuid){
        Boolean result = redisOauthAuthorizeTokenTemplate.delete(uuid.toString());
        return result != null && result;
    }

    /*
     * <p>임시토큰 조회 메서드 입니다. <br />
     * 임시토큰이 존재한다면 저장된 TokenDto 정보를 얻을 수 있습니다.
     * </p>
     */
    public Optional<TokenDto> getOauth2AuthorizeToken(String uuid) {
        String value = redisTemplate.opsForValue().get(uuid);
        if (value == null) {
            return Optional.empty();
        }
        try {
            TokenDto tokenDto = objectMapper.readValue(value, TokenDto.class);
            return Optional.of(tokenDto);
        } catch (IOException e) {
            // 적절한 예외 처리
            throw new RuntimeException("Error deserializing TokenDto", e);
        }
    }
}
