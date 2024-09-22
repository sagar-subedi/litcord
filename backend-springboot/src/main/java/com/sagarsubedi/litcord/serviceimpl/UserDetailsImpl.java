package com.sagarsubedi.litcord.serviceimpl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sagarsubedi.litcord.model.Profile;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class UserDetailsImpl implements UserDetails {
    @Getter
    private final Long id;
    @Getter
    private final String name;
    @Getter
    private final String email;
    @JsonIgnore
    private final String password;

    public UserDetailsImpl(
            Long id, String name, String email, String password
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static UserDetailsImpl build(Profile profile) {
        return new UserDetailsImpl(
                profile.getId(),
                profile.getName(),
                profile.getEmail(),
                profile.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
