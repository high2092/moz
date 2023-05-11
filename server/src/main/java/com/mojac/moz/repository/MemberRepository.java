package com.mojac.moz.repository;

import com.mojac.moz.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByOauthId(Long oauthId);

    @Modifying
    @Query("UPDATE Member m SET m.isReady = false")
    int init();
}
