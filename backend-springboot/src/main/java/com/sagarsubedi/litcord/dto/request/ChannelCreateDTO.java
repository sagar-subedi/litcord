package com.sagarsubedi.litcord.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChannelCreateDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    private Long profileId;

    @NotNull
    private Long serverId;

    // Default constructor
    public ChannelCreateDTO() {
    }

    // Constructor with all fields
    public ChannelCreateDTO(String name, Long profileId, Long serverId) {
        this.name = name;
        this.profileId = profileId;
        this.serverId = serverId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }
}
