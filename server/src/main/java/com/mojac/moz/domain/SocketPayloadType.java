package com.mojac.moz.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SocketPayloadType {
    LOCAL_CHAT("chat/local");

    private final String value;
}
