package com.mojac.moz.domain.quiz;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("I")
@Getter
public class ImageQuiz extends Quiz {
    private String url;
}
