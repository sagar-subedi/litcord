package com.sagarsubedi.litcord.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServerDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Invite code cannot be blank")
    private String inviteCode;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    // Constructor with fields
    public ServerDTO(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }
    public ServerDTO(String name, String inviteCode, Long userId) {
        this.name = name;
        this.userId = userId;
        this.inviteCode = inviteCode;
    }
}
