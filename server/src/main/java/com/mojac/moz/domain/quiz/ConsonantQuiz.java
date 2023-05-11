package com.mojac.moz.domain.quiz;

import com.mojac.moz.domain.Answer;
import com.mojac.moz.domain.Member;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@DiscriminatorValue("C")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConsonantQuiz extends Quiz {

    public ConsonantQuiz(String question, List<Answer> answers, Member member) {
        super(question, answers, member);
    }
}
