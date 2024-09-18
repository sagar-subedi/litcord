package com.sagarsubedi.litcord.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ServerCreateDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Invite code cannot be blank")
    private String inviteCode;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    // Default constructor
    public ServerCreateDTO() {
    }

    // Constructor with fields
    public ServerCreateDTO(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
