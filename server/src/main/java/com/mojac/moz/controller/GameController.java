package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.domain.Member;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.service.MemberService;
import com.mojac.moz.service.RoomService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final RoomService roomService;

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

    @PostMapping("/add-quiz")
    public void addQuiz(@RequestBody AddQuizRequest request, Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        Member member = memberRepository.findById(memberId).get();
        roomService.addQuiz(member.getRoom(), request.getQuizList());
    }

    @Getter
    static class AddQuizRequest {
        private List<Long> quizList;
    }

}
