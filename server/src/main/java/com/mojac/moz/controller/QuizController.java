package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.AnswerDto;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizDto;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.domain.quiz.QuizType;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.repository.QuizRepository;
import com.mojac.moz.service.QuizBundleService;
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
    private final QuizBundleService quizBundleService;

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

        List<Answer> answers = request.getAnswers().stream()
                .map((answerDto -> new Answer(answerDto.getAnswer(), answerDto.getScore())))
                .collect(Collectors.toList());

        Long quizId = quizService.saveQuiz(request.getType(), request.getQuestion(), answers, member);

        return new CreateQuizResponse(quizId);
    }

    @DeleteMapping("/{id}")
    public void deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
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
        private List<AnswerDto> answers;
    }

    @Getter
    @AllArgsConstructor
    static class CreateQuizResponse {
        private Long id;
    }
}
