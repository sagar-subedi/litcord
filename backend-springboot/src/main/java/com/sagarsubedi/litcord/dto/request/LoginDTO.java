package com.sagarsubedi.litcord.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTO {
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

    // Default constructor
    public LoginDTO() {
    }

    // Constructor with fields
    public LoginDTO(String password, String email) {
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
