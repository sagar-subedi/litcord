package com.sagarsubedi.litcord.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    public Account(String name,String email){
        this.name = name;
        this.email = email;
    }

    public Account(String name,String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Account(){

    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return  email;
    }
    public String getPassword() {
        return password;
    }
}
