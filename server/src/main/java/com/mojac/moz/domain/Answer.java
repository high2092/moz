package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {
    @Id @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String answer;
    private int score;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Answer(String answer, int score) {
        this.answer = answer;
        this.score = score;
    }

    public void register(Quiz quiz) {
        this.quiz = quiz;
    }
}
