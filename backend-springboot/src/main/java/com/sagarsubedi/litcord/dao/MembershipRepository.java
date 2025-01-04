package com.sagarsubedi.litcord.dao;
import com.sagarsubedi.litcord.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findAllByMembershipId(Long membershipId);
    List<Membership> findByUserId(Long memberId);
    Optional<Membership> findByUserIdAndServerId(Long userId, Long serverId);
    boolean existsByServerIdAndUserId(Long serverId, Long userId);
    List<Membership> findByServerId(Long serverId);
}
