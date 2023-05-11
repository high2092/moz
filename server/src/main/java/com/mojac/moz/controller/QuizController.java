package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizDto;
import com.mojac.moz.domain.quiz.ConsonantQuiz;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.domain.quiz.QuizType;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.repository.QuizRepository;
import com.mojac.moz.service.QuizService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;
    private final QuizService quizService;

    @GetMapping
    public GetQuizListResponse getQuizList(Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        Member member = memberRepository.findById(memberId).get();

        List<Quiz> quizList = quizRepository.findAllByMember(member);
        return new GetQuizListResponse(quizList.stream().map(quiz -> new QuizDto(quiz)).collect(Collectors.toList()));
    }

    @PostMapping
    public CreateQuizResponse createQuiz(@RequestBody CreateQuizRequest request, Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        Member member = memberRepository.findById(memberId).get();

        Long quizId = quizService.saveQuiz(request.getType(), request.getQuestion(), Arrays.asList(new Answer(request.getAnswer(), 5)), member);

        return new CreateQuizResponse(quizId);
    }

    @Getter
    @AllArgsConstructor
    static class GetQuizListResponse {
        private List<QuizDto> quizList;
    }

    @Getter
    static class CreateQuizRequest {
        private String type;
        private String question;
        private String answer;
    }

    @Getter
    @AllArgsConstructor
    static class CreateQuizResponse {
        private Long id;
    }

}
