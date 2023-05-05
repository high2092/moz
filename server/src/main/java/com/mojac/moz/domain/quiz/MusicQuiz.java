package com.mojac.moz.domain.quiz;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("M")
@Getter
public class MusicQuiz extends Quiz {
    private String videoId;
}
