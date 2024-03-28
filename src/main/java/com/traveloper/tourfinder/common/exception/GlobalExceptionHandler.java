package com.traveloper.tourfinder.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>Controller 단에서 발생하는 모든 에러를 중앙에서 관리하기 위한 핸들러입니다. <br/>
 * Filter 에서 발생한 에러는 Controller까지 전파되지 않으니 주의 해주세요.
 * </p>
 * */

@Getter
@AllArgsConstructor
public class GlobalExceptionHandler extends RuntimeException{
    private CustomGlobalErrorCode customGlobalErrorCode;
}
