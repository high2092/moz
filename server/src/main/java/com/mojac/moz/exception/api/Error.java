package com.mojac.moz.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {
    INVALID_QUIZ_TYPE("유효한 퀴즈 타입이 아닙니다.", 2001, HttpStatus.BAD_REQUEST),
    ROOM_IS_FULL("방이 꽉 찼습니다.", 2002, HttpStatus.CONFLICT),
    WRONG_PASSWORD("비밀번호가 틀렸습니다.", 2003, HttpStatus.FORBIDDEN);


    private final String message;
    private final int code;
    private final HttpStatus httpStatus;
}
