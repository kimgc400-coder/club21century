package com.toyproject.club21century.mapper;

import com.toyproject.club21century.domain.Member;
import com.toyproject.club21century.domain.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberMapperTest {

    @Autowired
    MemberMapper memberMapper;

    @Test
    @DisplayName("회원을 저장하고 조회하면 모든 필드가 매핑된다.")
    void findByLoginIdMappingAllField() {
        Member save = Member.signup("test1", "dummy", "test1@example.com", LocalDateTime.now());
        memberMapper.insert(save);

        Member found = memberMapper.findByLoginId("test1").orElseThrow();
        assertThat(found.getMemberId()).isNotNull();
        assertThat(found.getLoginId()).isEqualTo("test1");
        assertThat(found.getEmail()).isEqualTo("test1@example.com");
        assertThat(found.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(found.getCreatedAt()).isNotNull();
        assertThat(found.getDeletedAt()).isNull();

    }

    @Test
    @DisplayName("회원가입 시 ID가 채워지고 상태는 ACTIVE다")
    void insertSignupMember() {
        LocalDateTime now = LocalDateTime.of(2026,1, 1, 0, 0);
        Member member = Member.signup("newbie", "{bcrypt}$2a$10$dummy", "new@example.com", now);
        memberMapper.insert(member);

        assertThat(member.getMemberId()).isNotNull();

        Member found = memberMapper.findByLoginId("newbie").orElseThrow();
        assertThat(found.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(found.getCreatedAt()).isEqualTo(now);
        assertThat(found.getDeletedAt()).isNull();

    }

    @Test
    @DisplayName("탈퇴하면 개인정보가 익명화되고 조회되지 않는다.")
    void withdrawAnonymizes() {
        Member member = Member.signup("byebye", "{bcrypt}$2a$10$dummy",
                "bye@example.com", LocalDateTime.now());
        memberMapper.insert(member);
        Long id = member.getMemberId();

        member.withdraw(LocalDateTime.of(2026,3,1,0,0));
        int updated = memberMapper.withdraw(member);

        assertThat(updated).isEqualTo(1);
        assertThat(memberMapper.findById(id)).isEmpty();
        assertThat(memberMapper.findByLoginId("byebye")).isEmpty();
    }

}