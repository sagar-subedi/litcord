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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    private Long memberId;

    private Long serverId;

    private Long userId;

    public Member(Long serverId, Long userId){
        this.serverId = serverId;
        this.userId = userId;
    }
}
