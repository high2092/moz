package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizDto {
    private Long id;
    private String type;
    private String question;
    private List<AnswerDto> answers;

    public QuizDto(Quiz quiz) {
        this.id = quiz.getId();
        this.type = quiz.getType().getValue();
        this.question = quiz.getQuestion();
        this.answers = quiz.getAnswers().stream().map(answer -> new AnswerDto(answer)).collect(Collectors.toList());
    }
}
