package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.ConsonantQuiz;
import com.mojac.moz.domain.quiz.Quiz;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizDto {
    private Long id;
    private String consonant;
    private AnswerDto answer;

    public QuizDto(Quiz quiz) {
        this.id = quiz.getId();
        this.consonant = ((ConsonantQuiz) quiz).getConsonant();
        this.answer = new AnswerDto(quiz.getAnswers().get(0));
    }
}
