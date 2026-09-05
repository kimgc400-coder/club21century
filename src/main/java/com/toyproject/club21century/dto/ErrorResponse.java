package com.toyproject.club21century.dto;

import com.toyproject.club21century.exception.ErrorCode;

import java.util.List;

public record ErrorResponse(
        String code,
        String message,
        List<FieldError> errors
) {

    public record FieldError(
            //중첩 레코드. Spring의 FieldError와 이름은 같지만 다른 클래스이다.
            //암묵적으로 static이다.
            String field,//SignupRequest의 필드명이다.
            String message
    ) {
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), List.of());
    }

    public static ErrorResponse of(ErrorCode errorCode, List<FieldError> errors) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), errors);
    }


}
