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

        Member found = memberMapper.findByLoginId("test1");
        assertThat(found).isNotNull();
        assertThat(found.getMemberId()).isNotNull();
        //여기까지의 2줄은 데이터베이스에서 데이터를 성공적으로 조회해 왔고, Java 객체로의 매핑이 누락없이 이루어졌는지 검증하는 필수 적인 방어로직이다.
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

        Member found = memberMapper.findByLoginId("newbie");
        assertThat(found.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(found.getCreatedAt()).isEqualTo(now);
        assertThat(found.getDeletedAt()).isNull();

    }

}