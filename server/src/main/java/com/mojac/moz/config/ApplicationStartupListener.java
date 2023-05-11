package com.mojac.moz.config;

import com.mojac.moz.domain.RoomStatus;
import com.mojac.moz.repository.MemberRepository;
import com.mojac.moz.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roomRepository.init(RoomStatus.WAIT);
        memberRepository.init();
    }
}