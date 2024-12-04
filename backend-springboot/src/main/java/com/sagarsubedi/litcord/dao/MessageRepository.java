package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    // Get the last 'n' messages for a specific channelId, ordered by messageId descending
    @Query("SELECT m FROM Message m WHERE m.channelId = :channelId ORDER BY m.messageId DESC")
    List<Message> findLastNMessagesByChannelId(Long channelId, Pageable pageable);

}
