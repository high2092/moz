package com.mojac.moz.exception.internal;

public class QuizListEmptyException extends InternalException {

    public QuizListEmptyException() {
        super(Error.QUIZ_LIST_EMPTY.getMessage());
    }
}
