package com.sagarsubedi.litcord.service.member;
import com.sagarsubedi.litcord.dao.MembershipRepository;
import com.sagarsubedi.litcord.model.Membership;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }
    // Create a new member
    public Membership createMember(Membership member) {
        return membershipRepository.save(member);
    }

    // Get a member by ID
    public Optional<Membership> getMemberById(Long memberId) {
        return membershipRepository.findById(memberId);
    }

    // Get all members
    public List<Membership> getAllMembers() {
        return membershipRepository.findAll();
    }

    // Update a member
    public Membership updateMember(Long memberId, Membership updatedMember) {
        return membershipRepository.findById(memberId)
                .map(existingMembership -> {
                    existingMembership.setServerId(updatedMember.getServerId());
                    existingMembership.setUserId(updatedMember.getUserId());
                    return membershipRepository.save(existingMembership);
                })
                .orElseThrow(() -> new RuntimeException("Membership not found with ID: " + memberId));
    }

    // Delete a member
    public void deleteMembership(Long memberId) {
        if (membershipRepository.existsById(memberId)) {
            membershipRepository.deleteById(memberId);
        } else {
            throw new RuntimeException("Membership not found with ID: " + memberId);
        }
    }

    //Retrieves all memberships that a user belongs to
    //used to get all the servers a user is in
    public List<Membership> getMembersByMemberId(Long memberId) {
        return membershipRepository.findAllByMembershipId(memberId);
    }
}
