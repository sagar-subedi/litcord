package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByMemberId(Long memberId);
}
