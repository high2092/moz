package com.mojac.moz.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerDto {
    private String answer;
    private int score;

    public AnswerDto(Answer answer) {
        this.answer = answer.getAnswer();
        this.score = answer.getScore();
    }
}
