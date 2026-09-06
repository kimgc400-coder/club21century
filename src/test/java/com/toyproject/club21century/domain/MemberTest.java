package com.toyproject.club21century.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class MemberTest {

    @Test
    @DisplayName("가입한 회원은 ACTIVE 상태이고 탈퇴시각이 없다.")
    void signupCreatesActiveMember() {
        LocalDateTime now = LocalDateTime.of(2026,1,1,0,0);
        Member member = Member.signup("김유신", "{bcrypt}$2a$10$dummy", "usin@example.com", now);
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDeletedAt()).isNull();
        assertThat(member.getCreatedAt()).isEqualTo(now);
        assertThat(member.getModifiedAt()).isEqualTo(now);
        assertThat(member.getMemberId()).isNull();
    }

    @Test
    @DisplayName("탈퇴하면 status 와 deletedAt이 함께 바뀐다.")
    void withdrawSetsBoth() {
        Member member = Member.signup("bye", "dummy", "bye@example.com", LocalDateTime.now());
        LocalDateTime now = LocalDateTime.of(2026, 3, 1, 0, 0);

        member.withdraw(now);

        assertThat(member.getStatus()).isEqualTo(MemberStatus.WITHDRAWN);
        assertThat(member.getDeletedAt()).isEqualTo(now);
        assertThat(member.getModifiedAt()).isEqualTo(now);
    }
}
