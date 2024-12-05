package com.sagarsubedi.litcord.dummydata;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String name;
    private List<Server> servers;

    // Getters and Setters
    // Constructors
}
