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

    private Long channelId;

    private Long userId;

    public Member(Long channelId, Long userId){
        this.channelId = channelId;
        this.userId = userId;
    }
}
