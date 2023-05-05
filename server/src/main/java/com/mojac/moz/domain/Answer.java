package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Answer {
    @Id @GeneratedValue
    @Column(name = "answer_id")
    private Long id;

    private String answer;
    private int score;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
}
