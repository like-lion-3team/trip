package com.traveloper.tourfinder.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomGlobalErrorCode {
    // 서버에러
    SERVICE_UNAVAILABLE(503, "0001","Service Unavailable"),

    // 회원가입 에러
    EMAIL_ALREADY_EXIST(409, "1001", "이미 가입된 이메일."),
    NICKNAME_ALREADY_EXIST(409, "1002", "존재하는 닉네임."),
    PASSWORD_VALIDATION_FAILED(400, "1003", "비밀번호 유효성 검증 실패."),
    NOT_FOUND_MEMBER(400,"1004","존재하지 않는 사용자"),
    // 로그인 에러
    CREDENTIALS_NOT_MATCH(401, "2001", "아이디 또는 비밀번호가 일치하지 않습니다."),
    USER_BLOCKED(403, "2002", "차단된 사용자."),

    // JWT 관련 에러
    TOKEN_EXPIRED(401, "3001", "토큰이 만료되었습니다."),
    UNSUPPORTED_TOKEN(400, "3002", "지원하지 않는 토큰입니다."),
    MALFORMED_TOKEN(400, "3003", "잘못된 형식의 토큰입니다."),
    SIGNATURE_INVALID(400, "3004", "토큰의 서명이 유효하지 않습니다."),
    TOKEN_EMPTY(400, "3005", "토큰이 제공되지 않았습니다."),

    // 게시판 관련 에러
    ARTICLE_NOT_EXISTS(404, "4001", "존재하지 않는 게시글입니다."),
    ARTICLE_FORBIDDEN(403, "4002", "게시글의 소유 권한이 없습니다."),
    COMMENT_NOT_EXISTS(404, "4003", "존재하지 않는 댓글입니다."),
    ARTICLE_COMMENT_MISMATCH(401, "4004", "요청한 댓글이 해당 게시글의 댓글이 아닙니다."),
    COMMENT_FORBIDDEN(403, "4005", "댓글의 소유 권한이 없습니다."),

    // 코스 관련 에러
    COURSE_NOT_EXISTS(404, "5001", "존재하지 않는 코스입니다."),
    COURSE_FORBIDDEN(403, "5002", "코스의 소유 권한이 없습니다."),


    // oauth2
    NOT_SUPPORT_SOCIAL_PROVIDER(400,"6001","지원하지 않는 소셜 사업자 입니다."),

    // email
    PASSWORD_RECOVERY_CODE_MISS_MATCH(400,"7001","인증코드가 올바르지 않습니다"),
    NOT_FOUND_PASSWORD_RECOVERY_CODE(400,"7002","인증 코드가 만료 되었습니다.");

    private int status;
    private String code;
    private String message;
}
