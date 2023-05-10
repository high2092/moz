package com.mojac.moz.service;

import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.quiz.ConsonantQuiz;
import com.mojac.moz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional
    public Long createQuiz(String consonant, List<Answer> answers, Member member) {
        ConsonantQuiz quiz = new ConsonantQuiz(consonant, answers, member);
        quizRepository.save(quiz);
        answers.forEach(answer -> answer.register(quiz));
        return quiz.getId();
    }
}
