package com.toyproject.club21century.exception;

public class BusinessException extends RuntimeException{

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    //원인이 되는 예외를 함께 담는다.
    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

/*
 이 클래스의 역할은 Enum인 ErrorCode가 예외를 던질 수 없기 때문에 예외를 던질 수 있게 만드는 역할을 할 뿐이다.
 ErrorCode.DUPLICATE_MEMBER는 **"중복이라는 오류가 존재한다"**는 정의이다. 프로그램이 시작될 때 하나 만들어지고 계속 재사용된다.
 new BusinessException(DUPLICATE_MEMBER)는 **"방금 중복이 일어났다"**는 사건이다. 그리고 어디서 일어났는지를 스택트레이스로 갖고 있다.

 BusinessException: 이미 사용 중인 아이디 또는 이메일 입니다.
    at MemberService.signup(MemberService.java:36)   ← enum 은 이걸 못 가짐
    at MemberController.signup(MemberController.java:26)
 */
