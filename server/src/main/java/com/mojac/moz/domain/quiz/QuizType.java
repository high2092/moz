package com.mojac.moz.domain.quiz;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuizType {
    CONSONANT("consonant"),
    MUSIC("music");

    private final String value;
}
