package com.traveloper.tourfinder.common;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import com.traveloper.tourfinder.common.AppConstants;

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
}
