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

    public static Member signup(String loginId, String encodedPassword, String email, LocalDateTime now) {
        Member member = new Member();
        member.loginId = loginId;
        member.password = encodedPassword;
        member.email = email;
        member.status = MemberStatus.ACTIVE;
        member.createdAt = now;
        member.deletedAt = null;
        member.modifiedAt = now;
        return member;
    }

    public void withdraw(LocalDateTime now) {
        this.status = MemberStatus.WITHDRAWN;
        this.deletedAt = now;
        this.modifiedAt = now;
    }
}
