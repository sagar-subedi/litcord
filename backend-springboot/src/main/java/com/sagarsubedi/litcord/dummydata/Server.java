package com.sagarsubedi.litcord.dummydata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Server {
    private int id;
    private String name;
    private String imageUrl;
    private List<Channel> channels;

    // Getters and Setters
    // Constructors
}
