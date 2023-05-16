package com.mojac.moz.controller;

import com.mojac.moz.config.SecurityUtil;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizBundle;
import com.mojac.moz.domain.dto.CreateResponse;
import com.mojac.moz.domain.dto.QuizBundleDto;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.service.QuizBundleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz-bundle")
public class QuizBundleController {

    private final SecurityUtil securityUtil;
    private final MemberRepository memberRepository;
    private final QuizBundleService quizBundleService;

    @GetMapping
    public GetQuizBundleResponse getQuizBundleList(Authentication authentication) {
        Long memberId = securityUtil.getPrincipal(authentication);
        Member member = memberRepository.findById(memberId).get();

        List<QuizBundle> quizBundleList = quizBundleService.findQuizBundlesOfMember(member);

        return new GetQuizBundleResponse(quizBundleList.stream()
                .map(quizBundle -> new QuizBundleDto(quizBundle))
                .collect(Collectors.toList()));
    }

    @Getter
    @AllArgsConstructor
    static class GetQuizBundleResponse {
        private List<QuizBundleDto> quizBundleList;
    }

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
