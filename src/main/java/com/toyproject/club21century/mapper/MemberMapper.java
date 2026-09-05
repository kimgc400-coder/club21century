package com.toyproject.club21century.mapper;

import com.toyproject.club21century.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Select("SELECT COUNT(*) FROM member")
    long countAll();

    @Select("SELECT * FROM member WHERE login_id=#{loginId}")
    Member findByLoginId(String loginId);

    @Insert("""
            INSERT INTO member (login_id, password, email, status, created_at, modified_at) VALUES (#{loginId}, #{password}, #{email}, #{status}, #{createdAt}, #{modifiedAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    void insert(Member member);

    @Select("""
            SELECT * FROM member WHERE member_id = #{memberId} AND status <> 'WITHDRAWN'
            """)
    Optional<Member> findById(Long memberId);

}

/*
 * @Options(useGeneratedKeys = true, keyProperty = "memberId")
 * INSERT 후 MySql이 생성한 auto_increment 값을 member.memberId에 되돌려 넣는다.
 * 이게 없으면 방금 만든 회원 ID를 모른다.
 */