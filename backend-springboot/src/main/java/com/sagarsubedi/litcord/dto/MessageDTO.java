package com.sagarsubedi.litcord.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private String content;
    private Long senderId;
    private String senderEmail;
    private Long channelId;
}
