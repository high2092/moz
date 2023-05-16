package com.mojac.moz.repository;

import com.mojac.moz.domain.Member;
import com.mojac.moz.domain.QuizBundle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizBundleRepository extends JpaRepository<QuizBundle, Long> {

    List<QuizBundle> findAllByMember(Member member);
}
