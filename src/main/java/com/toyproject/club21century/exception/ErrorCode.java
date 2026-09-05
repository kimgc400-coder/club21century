package com.toyproject.club21century.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    //공통(Common == c)
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "C001", "입력값이 올바르지 않습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C002", "서버 오류가 발생했습니다."),

    //회원(Member == m)
    DUPLICATE_MEMBER(HttpStatus.CONFLICT, "M001", "이미 사용 중인 아이디 또는 이메일 입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M002", "회원을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
