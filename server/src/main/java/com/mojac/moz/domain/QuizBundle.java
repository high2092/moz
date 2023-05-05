package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class QuizBundle {
    @Id @GeneratedValue
    @Column(name = "quiz_bundle_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToMany
    @JoinTable(
            name = "quiz_quiz_bundle",
            joinColumns = @JoinColumn(name = "quiz_bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes = new ArrayList<>();

    private int thumbsUp = 0;
}
