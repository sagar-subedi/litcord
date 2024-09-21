package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findProfileByEmail(String email);
}
