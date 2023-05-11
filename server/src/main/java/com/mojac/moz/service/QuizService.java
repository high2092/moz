package com.mojac.moz.service;

import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.quiz.ConsonantQuiz;
import com.mojac.moz.domain.quiz.MusicQuiz;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.domain.quiz.QuizType;
import com.mojac.moz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional
    public Long saveQuiz(String type, String question, List<Answer> answers, Member member) {
        Quiz quiz = createQuiz(type, question, answers, member);
        quizRepository.save(quiz);
        answers.forEach(answer -> answer.register(quiz));
        return quiz.getId();
    }

    public Quiz createQuiz(String type, String question, List<Answer> answers, Member member) {
        if (type.equals(QuizType.CONSONANT.getValue())) {
            return new ConsonantQuiz(question, answers, member);
        } else if (type.equals(QuizType.MUSIC.getValue())) {
            return new MusicQuiz(question, answers, member);
        }

        throw new RuntimeException("유효한 퀴즈 타입이 아닙니다: " + type);
    }
}
