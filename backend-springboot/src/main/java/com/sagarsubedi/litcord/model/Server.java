package com.sagarsubedi.litcord.model;

import jakarta.persistence.*;

@Entity
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_seq")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "inviteCode", nullable = false, unique = true)
    private String inviteCode;

    @Column(name = "userId", nullable = false)
    private String userId;

    public Server(String name, String inviteCode, String userId) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.userId = userId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public String getUserId() {
        return userId;
    }
}