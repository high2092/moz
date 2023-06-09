package com.mojac.moz.repository;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findAllByMember(Member member);
}
