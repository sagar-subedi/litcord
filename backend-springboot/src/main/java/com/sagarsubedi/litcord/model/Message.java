package com.sagarsubedi.litcord.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    Long id;

    String content;

    Long senderId;

    Long channelId;

    String senderEmail;

    private LocalDateTime sentAt;

    // Todo: Add new fields for message sent date, deleted status, updatedAt, fileUrl, etc, iteratively (not every field needed to be added at once)
    public Message(){
        this.content = "Default Message";
    }

    public Message(String message, Long senderId, String senderEmail, Long channelId, LocalDateTime sentAt){
        this.content = message;
        this.senderId = senderId;
        this.channelId = channelId;
        this.senderEmail = senderEmail;
        this.sentAt = sentAt;
    }
}
