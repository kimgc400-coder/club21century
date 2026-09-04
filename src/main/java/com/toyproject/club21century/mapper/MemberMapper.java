package com.toyproject.club21century.mapper;

import com.toyproject.club21century.domain.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MemberMapper {

    @Select("SELECT COUNT(*) FROM member")
    long countAll();

    @Select("SELECT * FROM member WHERE login_id=#{loginId}")
    Member findByLoginId(String loginId);

    @Insert("""
            INSERT INTO member (login_id, password, email, status, created_at, modified_at) VALUES ('test1', 'dummy', 'test1@example.com','ACTIVE',NOW(), NOW())
            """)
    void insertForTest();

}
