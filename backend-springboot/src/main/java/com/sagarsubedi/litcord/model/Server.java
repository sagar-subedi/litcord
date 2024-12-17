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
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "server_seq")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "inviteCode", nullable = false, unique = true)
    private String inviteCode;

    @Column(name = "userId", nullable = false)
    private Long userId;

    private String dpUrl;

    public Server(String name, String inviteCode, Long userId) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.userId = userId;
    }
}