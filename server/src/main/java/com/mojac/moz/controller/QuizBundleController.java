package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.dto.CreateResponse;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.service.QuizBundleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/quiz-bundle")
public class QuizBundleController {

    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;
    private final QuizBundleService quizBundleService;

    @PostMapping
    public CreateResponse createQuizBundle(@RequestBody @Valid CreateQuizBundleRequest request, Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        Member member = memberRepository.findById(memberId).get();
        Long id = quizBundleService.createQuizBundle(request.getTitle(), request.getQuizzes(), member);
        return new CreateResponse(id);
    }

    @Getter
    static class CreateQuizBundleRequest {
        @NotEmpty private String title;
        @NotNull private List<Long> quizzes;
    }
}
