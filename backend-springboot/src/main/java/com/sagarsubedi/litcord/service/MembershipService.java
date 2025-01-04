package com.sagarsubedi.litcord.service;

import com.sagarsubedi.litcord.model.Membership;
import java.util.List;
import java.util.Optional;

public interface MembershipService {
    Membership createMembership(Membership membership);
    Optional<Membership> getMembershipById(Long membershipId);
    Optional<Membership> getMembershipByUserIdAndServerId(Long userId, Long serverId);
    List<Membership> getMembershipsByServerId(Long serverId);
    Membership updateMembership(Long membershipId, Membership membership);
    void deleteMembership(Long membershipId);
    Membership joinServer(String inviteCode, Long userId);
}
