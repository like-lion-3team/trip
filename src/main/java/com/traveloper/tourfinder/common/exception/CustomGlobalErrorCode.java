package com.traveloper.tourfinder.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomGlobalErrorCode {

    // 회원가입 에러
    EMAIL_ALREADY_EXIST(409, "1001", "이미 가입된 이메일."),
    NICKNAME_ALREADY_EXIST(409, "1002", "존재하는 닉네임."),
    PASSWORD_VALIDATION_FAILED(400, "1003", "비밀번호 유효성 검증 실패."),

    // 로그인 에러
    CREDENTIALS_NOT_MATCH(401, "2001", "아이디 또는 비밀번호가 일치하지 않습니다."),
    USER_BLOCKED(403, "2002", "차단된 사용자.");


    private int status;
    private String code;
    private String message;
}
