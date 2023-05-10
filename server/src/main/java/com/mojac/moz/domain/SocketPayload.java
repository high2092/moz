package com.mojac.moz.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocketPayload {
    private String type;
    private String body;
    @Setter private String from;
}
