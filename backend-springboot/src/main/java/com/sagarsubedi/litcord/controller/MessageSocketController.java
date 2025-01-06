package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.dto.MessageDTO;
import com.sagarsubedi.litcord.model.Message;
import com.sagarsubedi.litcord.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageSocketController {
    @Autowired
    MessageService messageService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{channelId}")// Receives messages from "/app/chat"
    public void sendMessage(@DestinationVariable Long channelId, MessageDTO message) {
        // Process message (e.g., save to database) before broadcasting
        Message savedMessage =  messageService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/messages/" + channelId, savedMessage);
    }
}
