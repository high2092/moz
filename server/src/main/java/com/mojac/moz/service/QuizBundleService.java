package com.mojac.moz.service;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizBundle;
import com.mojac.moz.domain.quiz.Quiz;
import com.mojac.moz.repository.QuizBundleRepository;
import com.mojac.moz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizBundleService {

    private final QuizRepository quizRepository;
    private final QuizBundleRepository quizBundleRepository;

    @Transactional(readOnly = true)
    public List<QuizBundle> findQuizBundlesOfMember(Member member) {
        return quizBundleRepository.findAllByMember(member);
    }

    @Transactional
    public Long createQuizBundle(String title, List<Long> quizIdList, Member member) {
        List<Quiz> quizzes = quizIdList.stream().map(id -> quizRepository.findById(id).get()).collect(Collectors.toList());
        QuizBundle quizBundle = new QuizBundle(title, quizzes, member);
        quizBundleRepository.save(quizBundle);
        return quizBundle.getId();
    }
}
