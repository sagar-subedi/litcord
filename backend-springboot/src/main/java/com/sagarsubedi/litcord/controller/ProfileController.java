package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ServerCreationConflictException;
import com.sagarsubedi.litcord.dto.request.ProfileCreateDTO;
import com.sagarsubedi.litcord.dto.request.ServerCreateDTO;
import com.sagarsubedi.litcord.model.Profile;
import com.sagarsubedi.litcord.model.Server;
import com.sagarsubedi.litcord.service.profile.ProfileService;
import com.sagarsubedi.litcord.service.server.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Handles user creation, deletion, update
@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping
    public ResponseEntity<String> createProfile(@RequestBody ProfileCreateDTO profile){
        try{
            Profile createProfile = profileService.createProfile(profile.getName(), profile.getEmail());
            return new ResponseEntity<>(createProfile.getId().toString(), HttpStatus.CREATED);
        }catch(ServerCreationConflictException e){
            return new ResponseEntity<>("Profile/User already present", HttpStatus.CONFLICT);
        }catch(Exception e){
            return new ResponseEntity<>("Something happened. Profile/User not created.", HttpStatus.CONFLICT);
        }
    }
}