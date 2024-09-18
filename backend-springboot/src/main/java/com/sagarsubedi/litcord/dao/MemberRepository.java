package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
