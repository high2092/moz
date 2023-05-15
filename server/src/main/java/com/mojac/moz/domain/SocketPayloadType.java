package com.mojac.moz.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocketPayloadType {
    SYSTEM("system"),
    LOCAL_CHAT("chat/local"),
    ROUND_INFO("roundInfo"),
    MUSIC_QUIZ("quiz/music");

    private final String value;
}
