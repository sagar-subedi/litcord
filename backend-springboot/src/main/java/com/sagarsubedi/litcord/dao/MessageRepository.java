package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByChannelIdOrderBySentAtAsc(Long channelId);

    Page<Message> findByChannelId(Long channelId, Pageable pageable);
}
