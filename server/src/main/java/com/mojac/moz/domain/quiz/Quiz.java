package com.mojac.moz.domain.quiz;

import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizBundle;
import com.mojac.moz.domain.Room;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Getter
public abstract class Quiz {
    @Id @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;

    @OneToMany
    @JoinColumn(name = "answer_id")
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(mappedBy = "quizzes")
    private List<QuizBundle> quizBundles = new ArrayList<>();

    @ManyToMany(mappedBy = "quizzes")
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
