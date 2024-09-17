package com.sagarsubedi.litcord.model;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long userId;

    private String name;

    @Column(unique = true)
    private String email;
    public User(String name,String email){
        this.name = name;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return  email;
    }
}
