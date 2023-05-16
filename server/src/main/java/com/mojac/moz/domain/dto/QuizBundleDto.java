package com.mojac.moz.domain.dto;

import com.mojac.moz.domain.QuizBundle;
import com.mojac.moz.domain.QuizDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizBundleDto {

    private Long id;
    private String title;
    private List<QuizDto> quizList;

    public QuizBundleDto(QuizBundle quizBundle) {
        this.id = quizBundle.getId();
        this.title = quizBundle.getTitle();
        this.quizList = quizBundle.getQuizzes().stream().map(quiz -> new QuizDto(quiz)).collect(Collectors.toList());
    }
}
