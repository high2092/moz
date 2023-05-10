package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String name;
    private int capacity;
    private String password;

    @OneToMany(mappedBy = "room")
    private List<Member> members = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @ManyToMany
    @JoinTable(
            name = "quiz_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes;
    private Long round;

    public void enter(Member member) {
        members.add(member);
    }

    public void leave(Member member) {
        this.members = this.members.stream()
                .filter(m -> m.getId() != member.getId())
                .collect(Collectors.toList());
    }

    public int size() {
        return members.size();
    }

    public boolean requirePassword() {
        return this.getPassword() != null;
    }

    public Room(String name, int capacity, String password) {
        this.name = name;
        this.capacity = capacity;
        this.password = password;
        this.status = RoomStatus.WAIT;
    }

    public boolean checkAllReady() {
        for (Member member : members) {
            if (member.getIsReady() == false) {
                return false;
            }
        }
        return true;
    }

    public void startGame() {
        status = RoomStatus.PLAYING;
    }

    public void addQuiz(List<Quiz> quizList) {
        this.quizzes.addAll(quizList);
    }
}
