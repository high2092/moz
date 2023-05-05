package com.mojac.moz.domain;

import lombok.Getter;

import java.util.List;

@Getter
public class Room {
    private Long id;
    private String name;
    private int capacity;
    private String password;
    private List<Member> members;
}
