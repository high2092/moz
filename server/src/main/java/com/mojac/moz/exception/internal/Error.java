package com.mojac.moz.exception.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Error {
    ROOM_STATUS_IS_NOT_PLAYING("게임 진행 중이 아닙니다.");

    private final String message;
}
