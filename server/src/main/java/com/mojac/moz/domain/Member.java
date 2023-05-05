package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<QuizBundle> quizBundles = new ArrayList<>();
}
