package com.toyproject.club21century.service;

import com.toyproject.club21century.domain.Member;
import com.toyproject.club21century.dto.SignupRequest;
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
            throw new IllegalStateException("이미 사용 중인 아이디 또는 이메일입니다.");
        }
        return member.getMemberId();
    }
}
