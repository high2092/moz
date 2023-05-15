package com.mojac.moz.domain.quiz;

import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizBundle;
import com.mojac.moz.domain.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id @GeneratedValue
    @Column(name = "quiz_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuizType type;

    private String question;

    @OneToMany(
            fetch = FetchType.EAGER,
            mappedBy = "quiz",
            cascade = CascadeType.ALL
    )
    private List<Answer> answers = new ArrayList<>();

    @ManyToMany(mappedBy = "quizzes")
    private List<QuizBundle> quizBundles = new ArrayList<>();

    @ManyToMany(mappedBy = "quizzes")
    private List<Room> rooms;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Quiz(String question, List<Answer> answers, Member member, QuizType type) {
        this.question = question;
        this.answers = answers;
        this.member = member;
        this.type = type;
    }
}
