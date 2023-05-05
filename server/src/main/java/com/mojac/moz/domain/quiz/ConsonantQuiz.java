package com.mojac.moz.domain.quiz;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("C")
@Getter
public class ConsonantQuiz extends Quiz {
    private String consonant;
}
