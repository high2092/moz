package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private Long oauthId;

    @OneToMany(mappedBy = "member")
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<QuizBundle> quizBundles = new ArrayList<>();

    // Transient
    @ManyToOne
    private Room room;
    private Boolean isReady;

    public Member(Long oauthId, String name) {
        this.name = name;
        this.oauthId = oauthId;
        this.isReady = false;
    }

    public void enterRoom(Room room) {
        this.room = room;
    }

    public void leaveRoom() {
        this.room = null;
    }

    @Transactional
    public void ready() {
        isReady = true;
    }

    public void unready() {
        isReady = false;
    }
}
