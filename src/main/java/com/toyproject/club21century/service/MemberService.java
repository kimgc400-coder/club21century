package com.toyproject.club21century.service;

import com.toyproject.club21century.domain.Member;
import com.toyproject.club21century.dto.MemberResponse;
import com.toyproject.club21century.dto.MemberUpdateRequest;
import com.toyproject.club21century.dto.SignupRequest;
import com.toyproject.club21century.exception.BusinessException;
import com.toyproject.club21century.exception.ErrorCode;
import com.toyproject.club21century.mapper.MemberMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberMapper memberMapper, PasswordEncoder passwordEncoder) {
        this.memberMapper = memberMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long signup(SignupRequest request) {
        Member member = Member.signup(
                request.loginId(),
                passwordEncoder.encode(request.password()),
                request.email(),
                LocalDateTime.now()
        );

        try {
            memberMapper.insert(member);
        } catch (DuplicateKeyException e) {//경합 문제에서의 최후의 보루를 DB에서 막는다.
            throw new BusinessException(ErrorCode.DUPLICATE_MEMBER, e);
        }
        return member.getMemberId();
    }

    public MemberResponse findById(Long memberId) {
        return memberMapper.findById(memberId)
                .map(MemberResponse::from)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberMapper.findById(memberId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        member.withdraw(LocalDateTime.now());
        memberMapper.withdraw(member);
    }

    @Transactional
    public MemberResponse update(Long memberId, MemberUpdateRequest request) {
        if (request.isEmpty()) {
            throw new BusinessException(ErrorCode.NOTHING_TO_UPDATE);
        }

        Member member = memberMapper.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        member.update(request.email(), request.nickname(), LocalDateTime.now());

        try {
            memberMapper.update(member);
        } catch (DuplicateKeyException e) {
            throw new BusinessException(ErrorCode.DUPLICATE_MEMBER, e);
        }

        return MemberResponse.from(member);
    }
}
