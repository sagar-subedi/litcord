package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.Exceptions.ProfileCreationConflictException;
import com.sagarsubedi.litcord.dto.LoginDTO;
import com.sagarsubedi.litcord.dto.request.ProfileCreateDTO;
import com.sagarsubedi.litcord.model.Profile;
import com.sagarsubedi.litcord.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            return authService.login(loginDTO);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody ProfileCreateDTO profileCreateDTO) {
        try {
            Profile createProfile = authService.register(profileCreateDTO);
            return new ResponseEntity<>(createProfile.getId().toString(), HttpStatus.CREATED);
        } catch (ProfileCreationConflictException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Something happened. Profile/User not created.", HttpStatus.CONFLICT);
        }
    }
}
