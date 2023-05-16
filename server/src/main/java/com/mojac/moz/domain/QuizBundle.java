package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizBundle {
    @Id @GeneratedValue
    @Column(name = "quiz_bundle_id")
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Long authorId;

    @ManyToMany
    @JoinTable(
            name = "quiz_quiz_bundle",
            joinColumns = @JoinColumn(name = "quiz_bundle_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes = new ArrayList<>();

    private int thumbsUp = 0;

    public QuizBundle(String title, List<Quiz> quizzes, Member member) {
        this.title = title;
        this.quizzes = quizzes;
        this.member = member;
        this.authorId = member.getId();
    }
}
