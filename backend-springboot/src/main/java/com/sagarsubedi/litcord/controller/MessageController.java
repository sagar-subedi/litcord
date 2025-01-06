package com.sagarsubedi.litcord.controller;

import com.sagarsubedi.litcord.dto.MessageDTO;
import com.sagarsubedi.litcord.model.Message;
import com.sagarsubedi.litcord.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody MessageDTO messageDTO) {
        Message savedMessage = messageService.saveMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<Message>> getMessagesByChannelId(
            @PathVariable Long channelId,
            @RequestParam int page,
            @RequestParam int size) {
        List<Message> messages = messageService.getMessagesByChannelId(channelId, page, size);
        return ResponseEntity.ok(messages);
    }
}
