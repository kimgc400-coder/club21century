package com.toyproject.club21century.dto;

import com.toyproject.club21century.domain.Member;
import com.toyproject.club21century.domain.MemberStatus;

import java.time.LocalDateTime;

public record MemberResponse(
        Long memberId,
        String loginId,
        String nickname,
        String email,
        MemberStatus status,
        LocalDateTime createdAt
) {
    // password 를 담을 자리가 없다. 그래서 응답에 샐 수 없다.
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getMemberId(),
                member.getLoginId(),
                member.getNickname(),
                member.getEmail(),
                member.getStatus(),
                member.getCreatedAt()
        );
    }
}
