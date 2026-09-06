package com.toyproject.club21century.mapper;

import com.toyproject.club21century.domain.Member;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    @Select("SELECT COUNT(*) FROM member")
    long countAll();

    @Select("SELECT * FROM member WHERE login_id=#{loginId}")
    Optional<Member> findByLoginId(String loginId);

    @Insert("""
            INSERT INTO member (login_id, password, email, status, created_at, modified_at) VALUES (#{loginId}, #{password}, #{email}, #{status}, #{createdAt}, #{modifiedAt})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "memberId")
    //여기에서 작성한 keyProperty="memberId"를 보고 넘겨받은 member객체의 memberId 필드에 리플렉션으로 써준다.
    //원본을 직접 고친다.
    void insert(Member member);

    @Select("""
            SELECT * FROM member WHERE member_id = #{memberId} AND status <> 'WITHDRAWN'
            """)
    Optional<Member> findById(Long memberId);


    @Update("""
            UPDATE member
            SET status = #{status},
            login_id = CONCAT('deleted_', member_id),
            email = CONCAT('deleted_', member_id, '@removed.local'),
            password = '',
            deleted_at = #{deletedAt},
            modified_at =#{modifiedAt}
            WHERE member_id = #{memberId}
            """)
    int withdraw(Member member);

}

/*
 * @Options(useGeneratedKeys = true, keyProperty = "memberId")
 * INSERT 후 MySql이 생성한 auto_increment 값을 member.memberId에 되돌려 넣는다.
 * 이게 없으면 방금 만든 회원 ID를 모른다.
 */