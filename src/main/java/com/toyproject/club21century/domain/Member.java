package com.toyproject.club21century.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter

@NoArgsConstructor
public class Member {

    private Long memberId;
    private String loginId;
    private String nickname;
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

    public void update(String email, String nickname, LocalDateTime now) {
        if (email != null) {
            this.email = email;
        }
        if (nickname != null) {
            this.nickname = nickname;
        }
        this.modifiedAt = now;
    }
}
