package com.mojac.moz.exception.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {
    ROOM_STATUS_IS_NOT_PLAYING("게임 진행 중이 아닙니다."),
    ALL_ROUND_ENDED("모든 라운드가 종료되었습니다."),
    NO_ROUND_EXISTS("라운드 정보가 없습니다."),
    QUIZ_LIST_EMPTY("퀴즈 목록이 비어 있습니다.");

    private final String message;
}
