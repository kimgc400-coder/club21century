package com.toyproject.club21century.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "아이디는 필수 입니다.")
        @Size(min = 4, max = 50, message= "아이디는 4자이상 50자 이하여야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$]",
        message = "아이디는 영문, 숫자, underbar만 사용할 수 있습니다.")
        String loginId,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min =8, max = 64, message = "비밀번호는 8자 이상 64자 이하여야 합니다.")
        String password,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @Size(max = 255)
        String email
) {
}
