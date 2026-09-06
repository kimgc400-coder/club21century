package com.toyproject.club21century.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 *  PATCH 요청. 보내지 않은 필드는 수정하지 않는다.
 *  null 이 "안 바꿈"을 뜻하므로 @NotBlank 를 쓰지 않는다.
 *  한계: "필드를 안 보냄"과 "null 로 지우기"를 구분할 수 없다.
 *        닉네임 지우기가 필요해지면 별도 엔드포인트를 만든다.
 * @param email
 * @param nickname
 */
public record MemberUpdateRequest(
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @Size(max = 255)
        String email,

        @Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하여야 합니다.")
        String nickname
        ) {
    public boolean isEmpty() {
        return email == null && nickname == null;
    }
}
