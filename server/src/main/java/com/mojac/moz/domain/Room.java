package com.mojac.moz.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    private Long id;
    private String name;
    private int capacity;
    private String password;
    private List<Member> members = new ArrayList<>();

    public void enter(Member member) {
        members.add(member);
    }

    public void leave(Member member) {
        this.members = this.members.stream()
                .filter(m -> m.getId() != member.getId())
                .collect(Collectors.toList());
    }

    public int size() {
        return members.size();
    }

    public boolean requirePassword() {
        return this.getPassword() != null;
    }

    public Room(String name, int capacity, String password) {
        this.name = name;
        this.capacity = capacity;
        this.password = password;
    }

    public void register(Long id) {
        this.id = id;
    }
}
