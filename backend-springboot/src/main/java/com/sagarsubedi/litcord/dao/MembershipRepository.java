package com.sagarsubedi.litcord.dao;
import com.sagarsubedi.litcord.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findAllByMembershipId(Long membershipId);
    boolean existsByUserIdAndServerId(Long serverId, Long userId);
}
