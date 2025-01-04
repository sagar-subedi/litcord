package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.dto.MembershipDTO;
import com.sagarsubedi.litcord.dto.ServerDTO;
import com.sagarsubedi.litcord.model.Membership;
import com.sagarsubedi.litcord.service.AccountService;
import com.sagarsubedi.litcord.service.MembershipService;
import com.sagarsubedi.litcord.service.ServerService;
import com.sagarsubedi.litcord.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

    private final MembershipService membershipService;
    private final ServerService serverService;
    private final AccountService accountService;

    public MembershipController(MembershipService membershipService, ServerService serverService, AccountService accountService) {
        this.membershipService = membershipService;
        this.serverService = serverService;
        this.accountService = accountService;
    }

    // Create Membership
    @PostMapping
    public ResponseEntity<Membership> createMembership(@RequestBody Membership membership) {
        Membership createdMembership = membershipService.createMembership(membership);
        return ResponseEntity.ok(createdMembership);
    }

    // Get Membership by ID
    @GetMapping("/{id}")
    public ResponseEntity<Membership> getMembershipById(@PathVariable Long id) {
        return membershipService.getMembershipById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get Memberships by Server ID
    @GetMapping("/server/{serverId}")
    public ResponseEntity<List<MembershipDTO>> getMembershipsByServerId(@PathVariable Long serverId) {
        List<Membership> memberships = membershipService.getMembershipsByServerId(serverId);
        // Map Membership to MembershipDTO
        List<MembershipDTO> membershipDTOs = memberships.stream()
                .map(membership -> {
                    String email = accountService.getAccountById(membership.getUserId()).getEmail();
                    return new MembershipDTO(membership, email);
                })
                .toList();

        return ResponseEntity.ok(membershipDTOs);
    }

    // Update Membership
    @PutMapping("/{id}")
    public ResponseEntity<Membership> updateMembership(@PathVariable Long id, @RequestBody Membership membership) {
        try {
            Membership updatedMembership = membershipService.updateMembership(id, membership);
            return ResponseEntity.ok(updatedMembership);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Membership
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Membership> deleteServer(@RequestParam Long serverId, @RequestParam Long userId){
        Membership membership = membershipService.getMembershipByUserIdAndServerId(userId, serverId).orElseThrow();
        membershipService.deleteMembership(membership.getMembershipId());
        return ResponseEntity.ok(membership);
    }

    @PostMapping("/join")
    public ResponseEntity<ServerDTO> joinServer(@RequestParam String inviteCode, @RequestParam Long userId) {
        Membership membership = membershipService.joinServer(inviteCode, userId);
        ServerDTO serverDTO = serverService.getServerById(membership.getServerId());
        serverDTO.setDpUrl(serverService.getServerImageUrl(serverDTO.getUserId(), serverDTO.getId(), StringUtils.extractFileName(serverDTO.getDpUrl())));
        return ResponseEntity.ok(serverDTO);
    }
}
