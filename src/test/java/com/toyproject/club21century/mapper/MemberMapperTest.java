package com.toyproject.club21century.mapper;

import com.toyproject.club21century.domain.Member;
import com.toyproject.club21century.domain.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
        memberMapper.insertForTest();

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

}