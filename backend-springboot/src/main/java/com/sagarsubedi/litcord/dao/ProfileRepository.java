package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
