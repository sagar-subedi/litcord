package com.sagarsubedi.litcord.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Channel {
    @Id
    private String id;
    private String name;
}
