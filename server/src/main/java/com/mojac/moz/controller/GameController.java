package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final SecurityUtil securityUtil;
    private final MemberService memberService;

    @PostMapping("/ready")
    public void ready(Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        memberService.ready(memberId);
    }

    @PostMapping("/unready")
    public void unready(Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        memberService.unready(memberId);
    }
}
