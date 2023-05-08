package com.mojac.moz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocketPayload {
    private String type;
    private String body;
    private String from;
}
