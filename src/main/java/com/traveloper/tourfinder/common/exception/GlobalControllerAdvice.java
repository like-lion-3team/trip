package com.traveloper.tourfinder.common.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 중앙 집중 예외처리를 위한 GlobalControllerAdvice 선언
 * RestController를 사용중이니 Advice도 @RestControllerAdvice를 사용한다.
 */
@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {


    /**
     * 에러 중앙처리 로직 작성완료
     */
    @ExceptionHandler(GlobalExceptionHandler.class)
    public ResponseEntity<?> applicationHandler(GlobalExceptionHandler e) {
        log.error("Error occurs {}", e.toString());

        // 에러 응답을 세팅한다.
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("code", e.getCustomGlobalErrorCode().getCode());
        errorResponse.put("message", e.getCustomGlobalErrorCode().getMessage());

        return ResponseEntity.status(e.getCustomGlobalErrorCode().getStatus())
                .body(errorResponse);
    }

}