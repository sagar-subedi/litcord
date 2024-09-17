package com.sagarsubedi.litcord.dao;

import com.sagarsubedi.litcord.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

}
