package com.sagarsubedi.litcord.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
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

    public Membership(Long serverId, Long userId){
        this.serverId = serverId;
        this.userId = userId;
    }
}
