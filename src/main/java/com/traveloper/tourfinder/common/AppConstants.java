package com.traveloper.tourfinder.common;


import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * <p>공통 상수를 정의합니다.</p>
 * */

public final class AppConstants {

    public static final  Integer ACCESS_TOKEN_EXPIRE_SECOND = 60 * 60;
    public static final  Integer REFRESH_TOKEN_EXPIRE_SECOND = 60 * 60 * 60;
}