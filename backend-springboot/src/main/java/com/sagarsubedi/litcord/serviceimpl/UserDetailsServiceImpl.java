package com.sagarsubedi.litcord.serviceimpl;

import com.sagarsubedi.litcord.dao.ProfileRepository;
import com.sagarsubedi.litcord.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final ProfileRepository profileRepository;

    public UserDetailsServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profile user = profileRepository.findProfileByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Error: User not found for the email: " + email));

        return UserDetailsImpl.build(user);
    }
}
