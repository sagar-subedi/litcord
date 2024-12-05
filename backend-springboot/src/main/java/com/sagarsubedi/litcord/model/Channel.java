package com.sagarsubedi.litcord.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    //Id of the admin of the channel
    @Column(name = "profileId", nullable = false)
    private Long profileId;

    @Column(name= "serverId", nullable = false)
    private Long serverId;

    public Channel(String name, Long profileId, Long serverId){
        this.name = name;
        this.profileId = profileId;
        this.serverId = serverId;
    }
}
