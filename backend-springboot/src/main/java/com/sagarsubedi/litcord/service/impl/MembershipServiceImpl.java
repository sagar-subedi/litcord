package com.sagarsubedi.litcord.service.impl;

import com.sagarsubedi.litcord.dao.ServerRepository;
import com.sagarsubedi.litcord.enums.MembershipType;
import com.sagarsubedi.litcord.model.Membership;
import com.sagarsubedi.litcord.dao.MembershipRepository;
import com.sagarsubedi.litcord.model.Server;
import com.sagarsubedi.litcord.service.MembershipService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final ServerRepository serverRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository, ServerRepository serverRepository) {
        this.membershipRepository = membershipRepository;
        this.serverRepository = serverRepository;
    }

    @Override
    public Membership createMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public Optional<Membership> getMembershipById(Long membershipId) {
        return membershipRepository.findById(membershipId);
    }

    @Override
    public List<Membership> getMembershipsByServerId(Long serverId) {
        return membershipRepository.findByServerId(serverId);
    }

    @Override
    public Membership updateMembership(Long membershipId, Membership membership) {
        if (membershipRepository.existsById(membershipId)) {
            membership.setMembershipId(membershipId);
            return membershipRepository.save(membership);
        } else {
            throw new IllegalArgumentException("Membership not found with id: " + membershipId);
        }
    }

    @Override
    public void deleteMembership(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }

    @Override
    public Optional<Membership> getMembershipByUserIdAndServerId(Long userId, Long serverId){
        return membershipRepository.findByUserIdAndServerId(userId, serverId);
    }

    @Transactional
    public Membership joinServer(String inviteCode, Long userId) {
        // Find the server by invite code
        Server server = serverRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid invite code"));

        // Check if the user is already a member
        boolean isAlreadyMember = membershipRepository.existsByServerIdAndUserId(server.getId(), userId);
        if (isAlreadyMember) {
            throw new IllegalStateException("User is already a member of the server");
        }

        // Create a new membership
        Membership membership = new Membership(server.getId(), userId, MembershipType.MEMBER);
        return  membershipRepository.save(membership);
    }
}
