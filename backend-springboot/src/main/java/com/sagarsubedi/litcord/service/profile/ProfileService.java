package com.sagarsubedi.litcord.service.profile;

import com.sagarsubedi.litcord.Exceptions.ProfileCreationConflictException;
import com.sagarsubedi.litcord.dao.ProfileRepository;
import com.sagarsubedi.litcord.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    ProfileRepository profileRepository;

    public Profile createProfile(String name, String email){
        Optional<Profile> profile = profileRepository.findProfileByEmail(email);
        if(profile.isPresent()){
            throw  new ProfileCreationConflictException("The email is already used by another profile");
        }
        return profileRepository.save(new Profile(name, email));
    }
}
