package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByEmail(String email);
}
