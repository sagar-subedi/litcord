package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ChannelRepository extends JpaRepository<Channel, Long> {
}
