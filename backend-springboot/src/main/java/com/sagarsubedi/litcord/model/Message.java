package com.sagarsubedi.litcord.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_seq")
    Long messageId;

    String message;

    String memberId;

    String channelId;

    // Todo: Add new fields for message sent date, deleted status, updatedAt, fileUrl, etc, iteratively (not everytfield needed to be added at once)
    public Message(){
        this.message = "Default Message";
        this.memberId = "Default Member";
    }

    public Message(String message, String memberId, String channelId){
        this.message = message;
        this.memberId = memberId;
        this.channelId = channelId;
    }
}
