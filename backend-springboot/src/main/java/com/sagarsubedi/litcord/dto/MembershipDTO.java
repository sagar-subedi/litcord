package com.sagarsubedi.litcord.dto;

import com.sagarsubedi.litcord.enums.MembershipType;
import com.sagarsubedi.litcord.model.Membership;
import lombok.Data;

@Data
public class MembershipDTO {
    private Long membershipId;
    private Long userId;
    private Long serverId;
    private MembershipType type;
    private String email;

    // Constructor to map Membership and email
    public MembershipDTO(Membership membership, String email) {
        this.membershipId = membership.getMembershipId();
        this.userId = membership.getUserId();
        this.serverId = membership.getServerId();
        this.type = membership.getType();
        this.email = email;
    }
}
