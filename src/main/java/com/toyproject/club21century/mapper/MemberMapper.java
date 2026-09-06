package com.toyproject.club21century.mapper;

import com.toyproject.club21century.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface MemberMapper {

    long countAll();

    Optional<Member> findByLoginId(String loginId);

    void insert(Member member);

    Optional<Member> findById(Long memberId);

    int withdraw(Member member);

}

