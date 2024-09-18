package com.sagarsubedi.litcord.model;

import jakarta.persistence.*;

@Entity
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "channel_seq")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
