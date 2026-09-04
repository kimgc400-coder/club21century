package com.toyproject.club21century.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
//@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class Member {

    private Long memberId;
    private String loginId;
    private String password;
    private String email;
    private MemberStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
}
