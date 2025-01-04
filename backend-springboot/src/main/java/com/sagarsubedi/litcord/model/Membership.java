package com.sagarsubedi.litcord.model;

import com.sagarsubedi.litcord.enums.MembershipType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_seq")
    private Long membershipId;

    private Long serverId;

    private Long userId;

    private MembershipType type;

    public Membership(Long serverId, Long userId, MembershipType type){
        this.serverId = serverId;
        this.userId = userId;
        this.type  = type;
    }
}
