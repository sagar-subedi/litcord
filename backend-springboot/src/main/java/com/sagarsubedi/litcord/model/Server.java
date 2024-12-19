package com.sagarsubedi.litcord.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "server", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Channel> channels = new ArrayList<>();

    public Server(String name, String inviteCode, Long userId) {
        this.name = name;
        this.inviteCode = inviteCode;
        this.userId = userId;
    }

    // Add a helper method to add a channel
    public void addChannel(Channel channel) {
        channel.setServer(this);
        this.channels.add(channel);
    }
}