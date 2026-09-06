package com.toyproject.club21century.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

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

    @Test
    @DisplayName("null인 필드는 바꾸지 않고 modifiedAt만 갱신한다.")
    void updateIgnoresNull() {
        LocalDateTime created = LocalDateTime.of(2026, 1, 1, 0, 0);
        Member member = Member.signup("usinKim", "dummy", "old@example.com", created);
        member.update(null, "길동이", created);

        LocalDateTime now = LocalDateTime.of(2026, 3, 1, 0, 0);
        member.update("new@example.com", null, now);

        assertThat(member.getEmail()).isEqualTo("new@example.com");
        assertThat(member.getNickname()).isEqualTo("길동이");
        assertThat(member.getModifiedAt()).isEqualTo(now);

    }

    @Test
    @DisplayName("둘 다 null 이어도 modifiedAt은 갱신된다.")
    void updateAlwaysTouchesModifiedAt() {
        LocalDateTime created = LocalDateTime.of(2026, 1, 1, 0, 0);
        Member member = Member.signup("usinKim", "dummy", "old@example.com", created);

        LocalDateTime now = LocalDateTime.of(2026, 3, 1, 0, 0);
        member.update(null, null, now);

        assertThat(member.getEmail()).isEqualTo("old@example.com");
        assertThat(member.getNickname()).isNull();
        assertThat(member.getModifiedAt()).isEqualTo(now);
        assertThat(member.getCreatedAt()).isEqualTo(created);
    }
}
