package com.sagarsubedi.litcord.service.member;

import com.sagarsubedi.litcord.dao.MemberRepository;
import com.sagarsubedi.litcord.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Create a new member
    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    // Get a member by ID
    public Optional<Member> getMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // Get all members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Update a member
    public Member updateMember(Long memberId, Member updatedMember) {
        return memberRepository.findById(memberId)
                .map(existingMember -> {
                    existingMember.setServerId(updatedMember.getServerId());
                    existingMember.setUserId(updatedMember.getUserId());
                    return memberRepository.save(existingMember);
                })
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));
    }

    // Delete a member
    public void deleteMember(Long memberId) {
        if (memberRepository.existsById(memberId)) {
            memberRepository.deleteById(memberId);
        } else {
            throw new RuntimeException("Member not found with ID: " + memberId);
        }
    }
}
