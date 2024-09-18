package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Optional<Channel> findByNameAndServerId(String name, Long serverId);
}
