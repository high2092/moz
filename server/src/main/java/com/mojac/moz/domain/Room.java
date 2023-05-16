package com.mojac.moz.domain;

import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.exception.internal.AllRoundEndedException;
import com.mojac.moz.exception.internal.NoRoundExistsException;
import com.mojac.moz.exception.internal.QuizListEmptyException;
import com.mojac.moz.exception.internal.RoomStatusIsNotPlayingException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "quiz_room",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private List<Quiz> quizzes;
    private Integer round;

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
        this.round = 1;
    }

    public int compare(String userAnswer) {
        for (Answer answer : getCurrentRoundAnswers()) {
            if (userAnswer.equals(answer.getAnswer())) {
                return answer.getScore();
            }
        }
        return 0;
    }

    public List<Answer> getCurrentRoundAnswers() {
        Quiz currentQuiz = this.quizzes.get(this.round - 1);
        if (currentQuiz != null) {
            return currentQuiz.getAnswers();
        }
        return Collections.emptyList();
    }

    public Quiz getCurrentRoundQuiz() {
        if (this.round == null) throw new NoRoundExistsException();
        if (this.quizzes.size() == 0) throw new QuizListEmptyException();
        return this.quizzes.get(this.round - 1);
    }

    public void skipRound() {
        if (this.status != RoomStatus.PLAYING) throw new RoomStatusIsNotPlayingException();
        if (this.round == quizzes.size()) throw new AllRoundEndedException();

        this.round++;
    }

    public void addQuiz(List<Quiz> quizList) {
        ArrayList<Quiz> copy = new ArrayList(quizList);
        Collections.shuffle(copy);
        this.quizzes.addAll(copy);
    }

    public void gameOver() {
        this.status = RoomStatus.WAIT;
    }
}
