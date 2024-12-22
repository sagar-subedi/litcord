package com.sagarsubedi.litcord.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ServerDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Invite code cannot be blank")
    private String inviteCode;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    private String dpUrl;

    private List<ChannelDTO> channels;

    // Constructor with fields
    public ServerDTO(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
    public ServerDTO(Long id, String name, String inviteCode, Long userId, String dpUrl, List<ChannelDTO> channels) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.inviteCode = inviteCode;
        this.dpUrl = dpUrl;
        this.channels = channels;
    }
}
