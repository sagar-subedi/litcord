package com.sagarsubedi.litcord.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profileId", nullable = false)
    private Long profileId;

    @Column(name= "serverId", nullable = false)
    private Long serverId;

    public Channel(String name, Long profileId, Long serverId){
        this.name = name;
        this.profileId = profileId;
        this.serverId = serverId;
    }

    public Channel(){

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getServerId() {
        return serverId;
    }

    public Long getProfileId() {
        return profileId;
    }
}
