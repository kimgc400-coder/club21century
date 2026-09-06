package com.toyproject.club21century.controller;

import com.toyproject.club21century.dto.MemberResponse;
import com.toyproject.club21century.dto.SignupRequest;
import com.toyproject.club21century.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        Long memberId = memberService.signup(request);
        return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponse> findById(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.findById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> withdraw(@PathVariable Long memberId) {
        memberService.withdraw(memberId);
        return ResponseEntity.noContent().build();
    }

}
