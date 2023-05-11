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
    private String question;
    private AnswerDto answer;

    public QuizDto(Quiz quiz) {
        this.id = quiz.getId();
        this.question = quiz.getQuestion();
        this.answer = new AnswerDto(quiz.getAnswers().get(0));
    }
}
